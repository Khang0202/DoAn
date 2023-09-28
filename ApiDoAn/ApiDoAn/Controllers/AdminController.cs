using Microsoft.AspNetCore.Mvc;

namespace ApiDoAn.Controllers
{
    public class AdminController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }
    }
}
