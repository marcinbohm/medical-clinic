namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class Location: LocationInput
    {
        public List<User>? locationUsersList { get; set; }
    }

    public class LocationPage
    {
        public List<Location>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }
    }

    public class LocationInput
    {
        public int? locationId { get; set; }
        public String? name { get; set; }
        public String? postalCode { get; set; }
        public String? city { get; set; }
        public String? street { get; set; }
        public DateTime? createdAt { get; set; }
        public DateTime? updatedAt { get; set; }
        public int? createdBy { get; set; }
        public int? updatedBy { get; set; }
    }

}
