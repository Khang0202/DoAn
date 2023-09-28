namespace ApiDoAn.Model
{
    public class WarningModel
    {
        public int iduser { get; set; }
        public int idprovince { get; set; }
        public int iddistrinct { get; set; }
        public int idcoordinates { get; set; }
        public int idaddress { get; set; }
        public double latitude { get; set; }
        public double longitude { get; set; }
        public string infoaddress { get; set; }
        public string townaddress { get; set; }
        public string infowarning { get; set; }
    }
}
