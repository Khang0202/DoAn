﻿using ApiDoAn.Model;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using RestSharp;
using RestSharp.Authenticators;
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
					else if (await checkExistEmail(model.email, connection))
					{
						return Ok(new { result = "Email already exists" });
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
								return Ok(new { result = "Account create failed" });
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
										new Claim(ClaimTypes.Name, username),
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
									reader.Close();
									return Ok(new
									{
										token = tokenString // Trả về token cho client
									});
								}
								else return Ok(new { result = "Invalid password" });
							}
							else return Ok(new { result = "Invalid username or password" });
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
		[Authorize]
		[HttpGet("getUserInfo")]
		public async Task<IActionResult> getUserInfo()
		{
			try
			{
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");

				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					await connection.OpenAsync();

					var username = HttpContext.User.FindFirst(ClaimTypes.Name).Value;

					Console.WriteLine($"{username}");

					string query = @"
                SELECT *
                FROM [dbo].[User]
                WHERE username = @username";

					using (SqlCommand command = new SqlCommand(query, connection))
					{
						command.Parameters.AddWithValue("@username", username);

						using (SqlDataReader reader = await command.ExecuteReaderAsync())
						{
							if (await reader.ReadAsync())
							{
								var userInfo = new
								{
									id = reader.GetInt32(reader.GetOrdinal("id")),
									firstname = reader.GetString(reader.GetOrdinal("firstname")),
									lastname = reader.GetString(reader.GetOrdinal("lastname")),
									birthday = reader.GetDateTime(reader.GetOrdinal("birthday")),
									email = reader.GetString(reader.GetOrdinal("email")),
									phone = reader.GetDecimal(reader.GetOrdinal("phone")),
									username = reader.GetString(reader.GetOrdinal("username")),
									roleid = reader.GetInt32(reader.GetOrdinal("roleid"))
								};

								return Ok(userInfo);
							}
							else
							{
								return Ok(new { result = "User not found" });
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
		[Authorize]
		[HttpPost("changePass")]
		public async Task<IActionResult> ChangePass([FromBody] ChangePassModel model)
		{
			try
			{
				var userId = HttpContext.User.FindFirst("id").Value;
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");

				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					await connection.OpenAsync();

					// Kiểm tra xem mật khẩu cũ có khớp với mật khẩu trong cơ sở dữ liệu không
					string checkPasswordQuery = @"
                SELECT [password]
                FROM [dbo].[User]
                WHERE id = @Userid
            ";

					using (SqlCommand checkPasswordCommand = new SqlCommand(checkPasswordQuery, connection))
					{
						checkPasswordCommand.Parameters.AddWithValue("@Userid", userId);

						using (SqlDataReader passwordReader = await checkPasswordCommand.ExecuteReaderAsync())
						{
							if (passwordReader.HasRows && passwordReader.Read())
							{
								string storedPasswordHash = passwordReader.GetString(passwordReader.GetOrdinal("password"));
								passwordReader.Close();
								bool isPasswordValid = BCrypt.Net.BCrypt.Verify(model.Oldpassword, storedPasswordHash);

								if (isPasswordValid)
								{
									// Nếu mật khẩu cũ khớp, thực hiện cập nhật mật khẩu mới
									string newPasswordHash = BCrypt.Net.BCrypt.HashPassword(model.Newpassword);

									string updatePasswordQuery = @"
                                UPDATE [dbo].[User]
                                SET [password] = @NewPassword
                                WHERE id = @Userid
                            ";

									using (SqlCommand updatePasswordCommand = new SqlCommand(updatePasswordQuery, connection))
									{
										updatePasswordCommand.Parameters.AddWithValue("@Userid", userId);
										updatePasswordCommand.Parameters.AddWithValue("@NewPassword", newPasswordHash);

										if (await updatePasswordCommand.ExecuteNonQueryAsync() > 0)
										{
											return Ok(new { result = "Password updated successfully" });
										}
										else
										{
											return Ok(new { result = "Password update failed" });
										}
									}
								}
								else
								{
									return Ok(new { result = "Invalid old password" });
								}
							}
							else return Ok(new { result = "Invaild" });

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
		[Authorize]
		[HttpPost("changeInfo")]
		public async Task<IActionResult> ChangeInfo([FromBody] ChangeInfoModel model)
		{
			try
			{
				var userId = HttpContext.User.FindFirst("id").Value;
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");


				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					await connection.OpenAsync();

					// Kiểm tra xem địa chỉ email mới đã tồn tại trong cơ sở dữ liệu chưa
					if (await checkExistEmail(model.Email, connection))
					{
						return Ok(new { result = "Email address already exists" });
					}

					string updateInfoQuery = @"
                UPDATE [dbo].[User]
                SET firstname = @FirstName, 
                    lastname = @LastName, 
                    birthday = @Birthday, 
                    email = @Email, 
                    phone = @Phone
                WHERE id = @UserId
            ";

					using (SqlCommand updateInfoCommand = new SqlCommand(updateInfoQuery, connection))
					{
						updateInfoCommand.Parameters.AddWithValue("@FirstName", model.FirstName);
						updateInfoCommand.Parameters.AddWithValue("@LastName", model.LastName);
						updateInfoCommand.Parameters.AddWithValue("@Birthday", model.Birth);
						updateInfoCommand.Parameters.AddWithValue("@Email", model.Email);
						updateInfoCommand.Parameters.AddWithValue("@Phone", model.Phone);
						updateInfoCommand.Parameters.AddWithValue("@UserId", userId);

						int rowsAffected = updateInfoCommand.ExecuteNonQuery();

						if (rowsAffected > 0)
						{
							return Ok(new { result = "User information updated successfully" });
						}
						else
						{
							return Ok(new { result = "Failed to update user information" });
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

		[HttpPost("forgotPass")]
		public async Task<IActionResult> ForgotPass([FromBody] ForgotPasswordModel model)
		{
			try
			{
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");

				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					await connection.OpenAsync();

					if (!await checkExistEmail(model.email, connection))
					{
						return Ok(new { result = "Email not exists" });
					}
					else
					{
						string newPassword = GenerateRandomPassword(10);
						// Băm mật khẩu ngẫu nhiên và cập nhật trong cơ sở dữ liệu
						string hashedPassword = BCrypt.Net.BCrypt.HashPassword(newPassword);
						await UpdateUserPassword(model.email, hashedPassword, connection);
						// Gửi email chứa mật khẩu ngẫu nhiên mới đến địa chỉ email của người dùng
						SendForgotPasswordEmail(model.email, newPassword);
						return Ok(new { result = "Password reset successful. \nCheck your email for the new password." });
						// Lấy địa chỉ email của người dùng từ cơ sở dữ liệu dựa trên tên người dùng (username)
					}
				}
			}
			catch (Exception ex)
			{
				Console.WriteLine("Error executing query: " + ex.Message);
				return StatusCode(500, new { Error = "Internal server error" });
			}
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
		private async Task<bool> checkExistEmail(string email, SqlConnection connection)
		{
			string query = @"
        SELECT COUNT(*)
        FROM [dbo].[User]
        WHERE email = @Email
    ";

			using (SqlCommand command = new SqlCommand(query, connection))
			{
				command.Parameters.AddWithValue("@Email", email);

				int count = (int)await command.ExecuteScalarAsync();
				return count > 0;
			}
		}
		private string GenerateRandomPassword(int length)
		{
			string upLetterChars = _configuration["RandomPass:UpLetter"];
			string specialChars = _configuration["RandomPass:Chars"];
			string numChars = _configuration["RandomPass:Num"];
			string letterChars = _configuration["RandomPass:Letter"];

			Random random = new Random();
			StringBuilder password = new StringBuilder(length);

			// Thêm ít nhất một ký tự in hoa, một ký tự đặc biệt, một số và một ký tự thường
			password.Append(upLetterChars[random.Next(upLetterChars.Length)]);
			password.Append(specialChars[random.Next(specialChars.Length)]);
			password.Append(numChars[random.Next(numChars.Length)]);
			password.Append(letterChars[random.Next(letterChars.Length)]);

			// Thêm các ký tự ngẫu nhiên còn lại
			for (int i = 4; i < length; i++)
			{
				string charSet;
				switch (random.Next(4))
				{
					case 0:
						charSet = upLetterChars;
						break;
					case 1:
						charSet = specialChars;
						break;
					case 2:
						charSet = numChars;
						break;
					default:
						charSet = letterChars;
						break;
				}
				password.Append(charSet[random.Next(charSet.Length)]);
			}

			// Trộn ngẫu nhiên chuỗi mật khẩu
			char[] passwordArray = password.ToString().ToCharArray();
			for (int i = 0; i < passwordArray.Length; i++)
			{
				int randIndex = random.Next(i, passwordArray.Length);
				char temp = passwordArray[i];
				passwordArray[i] = passwordArray[randIndex];
				passwordArray[randIndex] = temp;
			}

			return new string(passwordArray);
		}
		private void SendForgotPasswordEmail(string userEmail, string newPassword)
		{
			// Gửi email thông báo mật khẩu ngẫu nhiên mới đến địa chỉ email của người dùng bằng Mailgun
			RestClient client = new RestClient();
			client.BaseUrl = new Uri(_configuration["SendMail:API"]);
			client.Authenticator = new HttpBasicAuthenticator("api", _configuration["SendMail:KEY"]);

			RestRequest request = new RestRequest();
			request.AddParameter("domain", _configuration["SendMail:DOMAIN"], ParameterType.UrlSegment);
			request.Resource = "{domain}/messages";
			request.AddParameter("from", "Excited User <" + _configuration["SendMail:NAME"] + "@" + _configuration["SendMail:DOMAIN"] + "> ");
			request.AddParameter("to", userEmail);
			request.AddParameter("subject", "Password Reset");
			request.AddParameter("text", $"Your new password is: {newPassword}");
			request.Method = Method.POST;

			IRestResponse response = client.Execute(request);
		}
		private async Task UpdateUserPassword(string email, string hashedPassword, SqlConnection connection)
		{
			// Cập nhật mật khẩu mới băm vào cơ sở dữ liệu
			string query = @"
                UPDATE [dbo].[User]
                SET [password] = @Password
                WHERE email = @Email
            ";

			using (SqlCommand command = new SqlCommand(query, connection))
			{
				command.Parameters.AddWithValue("@Email", email);
				command.Parameters.AddWithValue("@Password", hashedPassword);

				await command.ExecuteNonQueryAsync();

			}
		}
	}
}
