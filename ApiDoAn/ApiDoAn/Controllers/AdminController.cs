using ApiDoAn.Model;
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
		private Timer _warningTimer;
		public AdminController(IConfiguration configuration, Timer warningTimer)
		{
			_configuration = configuration;
		}

		public AdminController(IConfiguration configuration)
		{
			_configuration = configuration;
		}
		[Authorize(Policy = "RequireAdminRole")]
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
					var timer = new Timer(DeactivateWarningCallback, id, 30 * 60 * 1000, Timeout.Infinite);
					return Ok(new { result = "Warning activated" });
				}
			}
			catch (Exception ex)
			{
				Console.WriteLine("Error executing query: " + ex.Message);
				return StatusCode(500, new { Error = "Internal server error" });
			}
		}
		private void DeactivateWarningCallback(object state)
		{
			int id = (int)state;
			//Vô hiệu hóa cảnh báo ở đây bằng cách sử dụng phương thức DeactivateWarning
			DeactivateWarning(id);
		}

		private void DeactivateWarning(int id)
		{
			try
			{
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");

				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					connection.Open();

					string updateQuery = "UPDATE dbo.Warning SET active = 0 WHERE id = @id";

					using (SqlCommand updateCommand = new SqlCommand(updateQuery, connection))
					{
						updateCommand.Parameters.AddWithValue("@id", id);
						int rowsAffected = updateCommand.ExecuteNonQuery();

						if (rowsAffected == 0)
						{
							Console.WriteLine("Failed to deactivate warning");
						}
						else
						{
							Console.WriteLine("Warning Deactivated");
						}
					}
				}
			}
			catch (Exception ex)
			{
				Console.WriteLine("Error executing query: " + ex.Message);
			}
		}

		[HttpPost("ChangeRole")]
		public async Task<IActionResult> changeRole([FromBody] ChangeRoleModel model)
		{
			try
			{
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");
				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					await connection.OpenAsync();
					string query = "UPDATE dbo.[user] SET roleid = @NewRoleId WHERE id = @UserId";
					using (SqlCommand updateCommand = new SqlCommand(query, connection))
					{
						updateCommand.Parameters.AddWithValue("@NewRoleId", model.roleid); // Gán giá trị cho tham số NewRoleId
						updateCommand.Parameters.AddWithValue("@UserId", model.id); // Gán giá trị cho tham số UserId

						int rowsAffected = updateCommand.ExecuteNonQuery();
						if (rowsAffected > 0)
						{
							return Ok(new { result = "Update success." });
						}
						else
						{
							return Ok(new { result = "User not found." });
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
		[HttpGet("ListUser")]
		public async Task<IActionResult> getListUser()
		{
			try
			{
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");
				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					await connection.OpenAsync();

					string query = @"
                SELECT id,username,firstname,email,roleid FROM dbo.[user]
            ";

					using (SqlCommand command = new SqlCommand(query, connection))
					{
						using (SqlDataReader reader = await command.ExecuteReaderAsync())
						{
							if (reader.HasRows)
							{
								var userList = new List<object>();

								while (reader.Read())
								{
									var userData = new
									{
										Id = reader.GetInt32(reader.GetOrdinal("id")),
										Username = reader.GetString(reader.GetOrdinal("username")),
										FirstName = reader.GetString(reader.GetOrdinal("firstname")),
										Email = reader.GetString(reader.GetOrdinal("email")),
										RoleId = reader.GetInt32(reader.GetOrdinal("roleid"))
									};

									userList.Add(userData);
								}

								return Ok(userList);
							}
							else
							{
								return BadRequest(new { result = "Not found list user" });
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
		[Authorize(Policy = "RequireAdminRole")]
		[HttpGet("getAllWarning")]
		public async Task<IActionResult> getAllWarning()
		{
			try
			{
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");
				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					await connection.OpenAsync();

					string query = @"
                SELECT w.id, u.firstname, p.province, d.district, a.town, a.route, a.streetNumber, w.info, c.latitude, c.longtitute, w.active
				FROM dbo.warning w
				INNER JOIN dbo.[User] u ON w.iduser = u.id
				INNER JOIN dbo.[Address] a ON w.idaddress = a.id
				INNER JOIN dbo.District d ON a.iddistrict = d.id
				INNER JOIN dbo.Province p ON d.idprovince = p.id
				INNER JOIN dbo.Coordinates c ON a.idcoordinates = c.id
				ORDER BY w.createdtime DESC;

            ";

					using (SqlCommand command = new SqlCommand(query, connection))
					{
						using (SqlDataReader reader = await command.ExecuteReaderAsync())
						{
							if (reader.HasRows)
							{
								var warningList = new List<object>();

								while (reader.Read())
								{
									var warningData = new
									{
										Id = reader.GetInt32(reader.GetOrdinal("id")),
										FirstName = reader.IsDBNull(reader.GetOrdinal("firstname")) ? null : reader.GetString(reader.GetOrdinal("firstname")),
										Province = reader.IsDBNull(reader.GetOrdinal("province")) ? null : reader.GetString(reader.GetOrdinal("province")),
										District = reader.IsDBNull(reader.GetOrdinal("district")) ? null : reader.GetString(reader.GetOrdinal("district")),
										Town = reader.IsDBNull(reader.GetOrdinal("town")) ? null : reader.GetString(reader.GetOrdinal("town")),
										Route = reader.IsDBNull(reader.GetOrdinal("route")) ? null : reader.GetString(reader.GetOrdinal("route")),
										StreetNumber = reader.IsDBNull(reader.GetOrdinal("streetNumber")) ? null : reader.GetString(reader.GetOrdinal("streetNumber")),
										Info = reader.IsDBNull(reader.GetOrdinal("info")) ? null : reader.GetString(reader.GetOrdinal("info")),
										Latitude = reader.GetDecimal(reader.GetOrdinal("latitude")),
										Longtitute = reader.GetDecimal(reader.GetOrdinal("longtitute")),
										Active = reader.GetBoolean(reader.GetOrdinal("active"))
									};

									warningList.Add(warningData);
								}

								return Ok(warningList);
							}
							else
							{
								return BadRequest(new { result = "Not found list warning" });
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

	}
}