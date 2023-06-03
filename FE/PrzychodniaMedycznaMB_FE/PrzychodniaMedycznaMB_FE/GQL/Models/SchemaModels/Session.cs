namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class Session : SessionInput
    {
        public User? user { get; set; }
    }

    public class SessionPage
    {
        public List<Session>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }
    }

    public class SessionInput
    {
        public int sessionId { get; set; }
        public int? userId { get; set; }
        public string? ticket { get; set; }
        public DateTime? lastActive { get; set; }
        public string? refreshTicket { get; set; }
    }

}
