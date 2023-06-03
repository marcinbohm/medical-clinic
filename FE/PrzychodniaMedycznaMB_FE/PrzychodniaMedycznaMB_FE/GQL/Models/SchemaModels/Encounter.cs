namespace PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels
{
    public class Encounter : EncounterInput
    {
        public Location? location { get; set; }
        public Patient? patient { get; set; }
        public Practitioner? practitioner { get; set; }
    }

    public class EncounterPage
    {
        public List<Encounter>? content { get; set; }
        public int? totalElements { get; set; }
        public int? totalPages { get; set; }
    }

    public class EncounterInput
    {
        public int? encounterId { get; set; }
        public int? patientId { get; set; }
        public int? practitionerId { get; set; }
        public String? description { get; set; }
        public DateTime? dateFrom { get; set; }
        public DateTime? dateTo { get; set; }
        public int? locationId { get; set; }
        public String? icd10 { get; set; }
        public DateTime? createdAt { get; set; }
        public DateTime? updatedAt { get; set; }
        public int? createdBy { get; set; }
        public int? updatedBy { get; set; }
    }
}
