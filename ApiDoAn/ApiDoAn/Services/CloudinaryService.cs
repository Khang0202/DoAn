using CloudinaryDotNet;
using CloudinaryDotNet.Actions;

namespace ApiDoAn.Services
{
	public class CloudinaryService
	{
		private Cloudinary _cloudinary;
		private readonly IConfiguration _configuration;
		public CloudinaryService(IConfiguration configuration)
		{
			_configuration = configuration;
			string cloudName = _configuration["Cloudinary:cloud_name"];
			string apiKey = _configuration["Cloudinary:api_key"];
			string apiSecret = _configuration["Cloudinary:api_secret"];
			Account account = new Account(cloudName, apiKey, apiSecret);
			_cloudinary = new Cloudinary(account);
		}

		public ImageUploadResult UploadImage(IFormFile image)
		{
			if (image != null && image.Length > 0)
			{
				var uploadParams = new ImageUploadParams
				{
					File = new FileDescription(image.FileName, image.OpenReadStream()),
				};

				return _cloudinary.Upload(uploadParams);
			}

			return null;
		}
	}
}

