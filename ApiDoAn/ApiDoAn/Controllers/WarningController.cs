using ApiDoAn.Model;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Data.SqlClient;

namespace ApiDoAn.Controllers
{
	[Authorize]
	[Route("api/warning")]
	[ApiController]
	public class WarningController : Controller
	{


		private readonly IConfiguration _configuration;
		public WarningController(IConfiguration configuration)
		{
			_configuration = configuration;
		}

		[HttpPost("addwaring")]
		public async Task<IActionResult> Addwarning([FromBody] AddWarningModel model)
		{
			try
			{
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");

				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					await connection.OpenAsync();
					using (SqlTransaction transaction = connection.BeginTransaction())
					{
						try
						{
							int coordinatesId = await InsertCoordinates(model.Coordinates, connection, transaction);
							Console.WriteLine("InsertCoordinates");
							int addressId = await InsertAddress(model.Address, coordinatesId, connection, transaction);
							Console.WriteLine("InsertAddress");
							if (await InsertWarning(model.Warning, addressId, connection, transaction))
							{
								transaction.Commit();
								return Ok(new { result = "Warning added successfully" });
							}
							else
							{
								transaction.Rollback();
								return BadRequest(new { result = "Failed to add Warning" });
							}
						}
						catch (Exception ex)
						{
							transaction.Rollback();
							Console.WriteLine("Error executing query: " + ex.Message);
							return StatusCode(500, new { Error = "Internal server error" });
						}
					}
				}
			}
			catch (Exception ex)
			{
				Console.WriteLine("Error opening connection: " + ex.Message);
				return StatusCode(500, new { Error = "Internal server error" });
			}
		}


		private async Task<int> InsertCoordinates(CoordinatesModel model, SqlConnection connection, SqlTransaction transaction)
		{
			string coordinatesQuery = @"
        INSERT INTO dbo.[Coordinates] (latitude, longtitute)
        VALUES (@Latitude, @Longtitude);
        SELECT SCOPE_IDENTITY();
    ";

			using (SqlCommand command = new SqlCommand(coordinatesQuery, connection, transaction))
			{
				command.Parameters.AddWithValue("@Latitude", model.latitude);
				command.Parameters.AddWithValue("@Longtitude", model.longitude);

				return Convert.ToInt32(await command.ExecuteScalarAsync());
			}
		}

		private async Task<int> InsertAddress(AddressModel model, int coordinatesId, SqlConnection connection, SqlTransaction transaction)
		{
			string addressQuery = @"
        INSERT INTO dbo.[Address](idprovince, iddistrict, town, route, streetNumber,idcoordinates)
        VALUES(@Idprovince, @Iddistrict, @Town, @Route, @StreetNumber,@Idcoordinates);
        SELECT SCOPE_IDENTITY();
    ";

			using (SqlCommand command = new SqlCommand(addressQuery, connection, transaction))
			{
				command.Parameters.AddWithValue("@Idprovince", model.idprovince);
				command.Parameters.AddWithValue("@Iddistrict", model.iddistrinct);
				command.Parameters.AddWithValue("@Town", model.town);
				command.Parameters.AddWithValue("@Route", model.route);
				command.Parameters.AddWithValue("@StreetNumber", model.streetNumber);
				command.Parameters.AddWithValue("@Idcoordinates", coordinatesId);


				return Convert.ToInt32(await command.ExecuteScalarAsync());
			}
		}

		private async Task<bool> InsertWarning(WarningModel model, int addressId, SqlConnection connection, SqlTransaction transaction)
		{
			var userId = HttpContext.User.FindFirst("id").Value;

			string warningQuery = @"
        INSERT INTO dbo.[Warning](iduser, idaddress, info, createdtime)
        VALUES(@Iduser,@IdAddress, @Infowarning, @Createdtime);
    ";

			using (SqlCommand command = new SqlCommand(warningQuery, connection, transaction))
			{

				command.Parameters.AddWithValue("@Iduser", int.Parse(userId));
				command.Parameters.AddWithValue("@IdAddress", addressId);
				command.Parameters.AddWithValue("@Infowarning", model.infowarning);
				//Đặt múi giờ cục bộ là múi giờ của Việt Nam. Lấy thời gian hiện tại theo múi giờ của Việt Nam
				DateTime vietnamTime = TimeZoneInfo.ConvertTimeFromUtc(DateTime.UtcNow, TimeZoneInfo.FindSystemTimeZoneById("SE Asia Standard Time"));
				command.Parameters.AddWithValue("@Createdtime", vietnamTime);

				return await command.ExecuteNonQueryAsync() > 0;
			}
		}
		[HttpGet("getAllActiveWarning")]
		public async Task<IActionResult> getAllActiveWarning()
		{
			try
			{
				string connectionString = _configuration.GetConnectionString("SqlServerConnection");
				using (SqlConnection connection = new SqlConnection(connectionString))
				{
					await connection.OpenAsync();

					string query = @"
                SELECT w.id, u.firstname, p.province, d.district, a.town, a.route, a.streetNumber, w.info, c.latitude, c.longtitute
				FROM dbo.warning w
				INNER JOIN dbo.[User] u ON w.iduser = u.id
				INNER JOIN dbo.[Address] a ON w.idaddress = a.id
				INNER JOIN dbo.District d ON a.iddistrict = d.id
				INNER JOIN dbo.Province p ON a.idprovince = p.id
				INNER JOIN dbo.Coordinates c ON a.idcoordinates = c.id
				WHERE active = 1
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
										Longtitute = reader.GetDecimal(reader.GetOrdinal("longtitute"))

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
