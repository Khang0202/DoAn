using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using System.Data.SqlClient;


namespace ApiDoAn.Controllers
{
    //Chưa có trong docs
    [Route("api/address")]
    [ApiController]
    public class AddressController : Controller
    {
        private readonly IConfiguration _configuration;
        public AddressController(IConfiguration configuration)
        {
            _configuration = configuration;
        }
        [HttpGet("getProvince")]
        public async Task<IActionResult> GetProvinceList()
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT * FROM dbo.Province
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            if (reader.HasRows)
                            {
                                var provinceList = new List<object>();

                                while (reader.Read())
                                {
                                    var provinceData = new
                                    {
                                        Id = reader.GetInt32(reader.GetOrdinal("id")),
                                        Name = reader.GetString(reader.GetOrdinal("province")),
                                    };

                                    provinceList.Add(provinceData);
                                }

                                return Ok(provinceList);
                            }
                            else
                            {
                                return BadRequest(new { result = "No provinces found" });
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
        [HttpGet("getDistrict")]
        public async Task<IActionResult> GetDistrictList()
        {
            try
            {
                string connectionString = _configuration.GetConnectionString("SqlServerConnection");
                using (SqlConnection connection = new SqlConnection(connectionString))
                {
                    await connection.OpenAsync();

                    string query = @"
                SELECT * FROM dbo.District
            ";

                    using (SqlCommand command = new SqlCommand(query, connection))
                    {
                        using (SqlDataReader reader = await command.ExecuteReaderAsync())
                        {
                            if (reader.HasRows)
                            {
                                var districtList = new List<object>();

                                while (reader.Read())
                                {
                                    var districtData = new
                                    {
                                        id = reader.GetInt32(reader.GetOrdinal("id")),
                                        //idprovince = reader.GetInt32(reader.GetOrdinal("idprovince")),
                                        name = reader.GetString(reader.GetOrdinal("district")),
                                    };

                                    districtList.Add(districtData);
                                }

                                return Ok(districtList);
                            }
                            else
                            {
                                return BadRequest(new { result = "No districts found" });
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


