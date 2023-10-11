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
                   // Đặt múi giờ cục bộ là múi giờ của Việt Nam
                TimeZoneInfo vietnamTimeZone = TimeZoneInfo.FindSystemTimeZoneById("SE Asia Standard Time");

                // Lấy thời gian hiện tại theo múi giờ của Việt Nam
                DateTime vietnamTime = TimeZoneInfo.ConvertTimeFromUtc(DateTime.UtcNow, vietnamTimeZone);

                command.Parameters.AddWithValue("@Createdtime", vietnamTime);

                return await command.ExecuteNonQueryAsync() > 0;
            }
        }
    }
}
