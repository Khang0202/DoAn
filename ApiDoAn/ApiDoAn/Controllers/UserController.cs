using ApiDoAn.Model;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.Data.SqlClient;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

namespace ApiDoAn.Controllers
{
    [Route("api/user")]
    [ApiController]
    public class UserController : Controller
    {
        private readonly IConfiguration _configuration;
        public UserController(IConfiguration configuration)
        {
            _configuration = configuration;
        }


        private async Task<bool> checkExistUser(string username, SqlConnection connection)
        {
            string query = @"
        SELECT COUNT(*)
        FROM [dbo].[User]
        WHERE username = @Username 
    ";

            using (SqlCommand command = new SqlCommand(query, connection))
            {
                command.Parameters.AddWithValue("@Username", username);

                int count = (int)await command.ExecuteScalarAsync();
                return count > 0;
            }
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register([FromBody] RegisterModel model)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    if (await checkExistUser(model.username, connection))
                    {
                        return Ok(new { result = "Username already exists" });
                    }
                    else
                    {

                        string hashedPassword = BCrypt.Net.BCrypt.HashPassword(model.password);

                        string query = @"
                            INSERT INTO [dbo].[User] (firstname, lastname, email, phone, username, [password],birthday)
                            VALUES (@FirstName, @LastName, @Email, @Phone, @Username, @Password, @birth);
                        ";

                        using (SqlCommand command = new SqlCommand(query, connection))
                        {
                            command.Parameters.AddWithValue("@FirstName", model.firstname);
                            command.Parameters.AddWithValue("@LastName", model.lastname);
                            command.Parameters.AddWithValue("@Email", model.email);
                            command.Parameters.AddWithValue("@Phone", model.phone);
                            command.Parameters.AddWithValue("@Username", model.username);
                            command.Parameters.AddWithValue("@Password", hashedPassword);
                            command.Parameters.AddWithValue("@birth", model.birth);

                            if (await command.ExecuteNonQueryAsync() > 0)
                            {
                                return Ok(new { result = "Account created" });
                            }
                            else
                            {
                                return BadRequest(new { result = "Account create failed" });
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error executing query: " + ex.Message);
                return StatusCode(500, new { Error = "Internal server error" });
            }
        }
        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] LoginModel model)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT username, [password], id, firstname, email,roleid
                FROM [dbo].[User]
                WHERE username = @Username
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@Username", model.username);

                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            if (reader.HasRows && reader.Read())
                            {
                                string storedPasswordHash = reader.GetString(reader.GetOrdinal("password"));
                                bool isPasswordValid = BCrypt.Net.BCrypt.Verify(model.password, storedPasswordHash);

                                if (isPasswordValid)
                                {
                                    // Lấy thông tin người dùng  
                                    int userid = reader.GetInt32(reader.GetOrdinal("id"));
                                    string username = reader.GetString(reader.GetOrdinal("username"));
                                    int roleid = reader.GetInt32(reader.GetOrdinal("roleid"));

                                    // Tạo JWT Token
                                    // Lay id user,Username,Lay role de tao xac thuc
                                    var claims = new[]
                                    {
                                        new Claim("id",userid.ToString()),
                                        new Claim(JwtRegisteredClaimNames.Sub, username),
                                        new Claim(ClaimTypes.Role, roleid.ToString())
                                    };

                                    var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["Jwt:Key"]));
                                    var signIn = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
                                    var token = new JwtSecurityToken(
                                        _configuration["Jwt:Issuer"],
                                        _configuration["Jwt:Audience"],
                                        claims,
                                        expires: null,//DateTime.UtcNow.AddMinutes(10), // Thời gian hết hạn của token
                                        signingCredentials: signIn);

                                    var tokenString = new JwtSecurityTokenHandler().WriteToken(token);

                                    return Ok(new
                                    {
                                        username = model.username,
                                        token = tokenString // Trả về token cho client
                                    });
                                }
                            }
                        }
                    }
                }

                return Unauthorized(new { result = "Invalid username or password" });
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error executing query: " + ex.Message);
                return StatusCode(500, new { Error = "Internal server error" });
            }
        }

        //[HttpPost("ForgotPass")]

    }
}
