using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Data.SqlClient;

namespace ApiDoAn.Controllers
{
    //Xac minh role
    [Authorize(Roles = "1")]
    [Route("api/Admin")]
    [ApiController]
    public class AdminController : Controller
    {
        private readonly IConfiguration _configuration;
        public AdminController(IConfiguration configuration)
        {
            _configuration = configuration;
        }

        [HttpGet("activeWarning")]
        public async Task<IActionResult> ActiveWarning(int id)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string checkQuery = "SELECT COUNT(*) FROM dbo.Warning WHERE id = @id";

                    using (SqlCommand checkCommand = new SqlCommand(checkQuery, connection))
                    {
                        checkCommand.Parameters.AddWithValue("@id", id);

                        int warningCount = (int)await checkCommand.ExecuteScalarAsync();

                        if (warningCount == 0)
                        {
                            return BadRequest(new { result = "No warning found" });
                        }
                    }

                    string updateQuery = "UPDATE dbo.Warning SET active = 1 WHERE id = @id";

                    using (SqlCommand updateCommand = new SqlCommand(updateQuery, connection))
                    {
                        updateCommand.Parameters.AddWithValue("@id", id);
                        int rowsAffected = updateCommand.ExecuteNonQuery();

                        if (rowsAffected == 0)
                        {
                            return BadRequest(new { result = "Failed to activate warning" });
                        }
                    }

                    return Ok(new { result = "Warning activated" });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error executing query: " + ex.Message);
                return StatusCode(500, new { Error = "Internal server error" });
            }
        }
        [HttpGet("DeactiveWarning")]
        public async Task<IActionResult> DeactiveWarning(int id)
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");

                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string checkQuery = "SELECT COUNT(*) FROM dbo.Warning WHERE id = @id";

                    using (SqlCommand checkCommand = new SqlCommand(checkQuery, connection))
                    {
                        checkCommand.Parameters.AddWithValue("@id", id);

                        int warningCount = (int)await checkCommand.ExecuteScalarAsync();

                        if (warningCount == 0)
                        {
                            return BadRequest(new { result = "No warning found" });
                        }
                    }

                    string updateQuery = "UPDATE dbo.Warning SET active = 0 WHERE id = @id";

                    using (SqlCommand updateCommand = new SqlCommand(updateQuery, connection))
                    {
                        updateCommand.Parameters.AddWithValue("@id", id);
                        int rowsAffected = updateCommand.ExecuteNonQuery();

                        if (rowsAffected == 0)
                        {
                            return BadRequest(new { result = "Failed to activate warning" });
                        }
                    }

                    return Ok(new { result = "Warning Deactivated" });
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error executing query: " + ex.Message);
                return StatusCode(500, new { Error = "Internal server error" });
            }
        }


    }
}