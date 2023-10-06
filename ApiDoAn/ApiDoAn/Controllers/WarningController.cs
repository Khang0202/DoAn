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
        public async Task<IActionResult> addwarning([FromBody] WarningModel model)
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
                            int coordinatesId = await InsertCoordinates(model, connection, transaction);
                            int addressId = await InsertAddress(model, coordinatesId, connection, transaction);
                            if (await InsertWarning(model, addressId, connection, transaction))
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


        private async Task<int> InsertCoordinates(WarningModel model, SqlConnection connection, SqlTransaction transaction)
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

        private async Task<int> InsertAddress(WarningModel model, int coordinatesId, SqlConnection connection, SqlTransaction transaction)
        {
            string addressQuery = @"
        INSERT INTO dbo.[Address](idprovince, iddistrict, route, streetNumber, idcoordinates)
        VALUES(@Idprovince, @Iddistrict, @Town, @Infoaddress, @Idcoordinates);
        SELECT SCOPE_IDENTITY();
    ";

            using (SqlCommand command = new SqlCommand(addressQuery, connection, transaction))
            {
                command.Parameters.AddWithValue("@Idprovince", model.idprovince);
                command.Parameters.AddWithValue("@Iddistrict", model.iddistrinct);
                command.Parameters.AddWithValue("@Town", model.townaddress);
                command.Parameters.AddWithValue("@Infoaddress", model.infoaddress);
                command.Parameters.AddWithValue("@Idcoordinates", coordinatesId);

                return Convert.ToInt32(await command.ExecuteScalarAsync());
            }
        }

        private async Task<bool> InsertWarning(WarningModel model, int addressId, SqlConnection connection, SqlTransaction transaction)
        {
            string warningQuery = @"
        INSERT INTO dbo.[Warning](iduser, idaddress, info, createdtime)
        VALUES(@Iduser, @Idaddress, @Infowarning, @Createdtime);
    ";

            using (SqlCommand command = new SqlCommand(warningQuery, connection, transaction))
            {
                command.Parameters.AddWithValue("@Iduser", model.iduser);
                command.Parameters.AddWithValue("@Idaddress", addressId);
                command.Parameters.AddWithValue("@Infowarning", model.infowarning);
                command.Parameters.AddWithValue("@Createdtime", DateTime.UtcNow);

                return await command.ExecuteNonQueryAsync() > 0;
            }
        }
    }
}
