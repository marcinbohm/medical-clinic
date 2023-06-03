namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class Examination
    {
        public int examinationId { get; set; }
        public int? encounterId { get; set; }

        public String? name { get; set; }
        public String? description { get; set; }
        public String? icd9 { get; set; }
        public DateTime? executionDate { get; set; }
        public DateTime? createdAt { get; set; }
        public DateTime? updatedAt { get; set; }
        public int? createdBy { get; set; }
        public int? updatedBy { get; set; }
    }

    public class ExaminationPage
    {
        public List<Examination>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }
    }

    public class ExaminationInput
    {
        public int? examinationId { get; set; }
        public int? encounterId { get; set; }
        public String? name { get; set; }
        public String? description { get; set; }
        public String? icd9 { get; set; }
        public DateTime? executionDate { get; set; }
        public DateTime? createdAt { get; set; }
        public DateTime? updatedAt { get; set; }
        public int? createdBy { get; set; }
        public int? updatedBy { get; set; }
    }
}
