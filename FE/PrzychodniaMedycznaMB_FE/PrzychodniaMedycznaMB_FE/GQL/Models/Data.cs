using PrzychodniaMedycznaMB_FE.GQL.Models.SchemaModels;

namespace PrzychodniaMedycznaMB_FE.GQL.Models
{
    public class Data
    {
        public string? totalElements;
        public string? totalPages;


        public CurrentUser? currentUser { get; set; }

        public EncounterPage? encounterPageByLocationIdAndUserPatient { get; set; }

        public User? user { get; set; }

        public PatientPage? userPageByLocationIdAndUserPatient { get; set; }

        public ExaminationPage? examinationPageByEncounterId { get; set; }

        public List<Location>? locationList { get; set; }

        public PractitonerPage? userPageByLocationIdAndUserDoctor { get; set; }

        public List<User>? usersList { get; set; }

        #region Mutations
        public OperationStatus? addEncounter { get; set; }

        public OperationStatus? updateEncounter { get; set; }

        public OperationStatus? deleteEncounter { get; set; }

        public OperationStatus? addCategory { get; set; }

        public OperationStatus? updateCategory { get; set; }

        public OperationStatus? deleteCategory { get; set; }

        public OperationStatus? addExamination { get; set; }

        public OperationStatus? updateExamination { get; set; }

        public OperationStatus? deleteExamination { get; set; }

        public OperationStatus? addLocation { get; set; }

        public OperationStatus? updateLocation { get; set; }

        public OperationStatus? deleteLocation { get; set; }

        public OperationStatus? addPermission { get; set; }

        public OperationStatus? updatePermission { get; set; }

        public OperationStatus? deletePermission { get; set; }

        public OperationStatus? addSession { get; set; }

        public OperationStatus? updateSession { get; set; }

        public OperationStatus? deleteSession { get; set; }

        public OperationStatus? addUser { get; set; }

        public OperationStatus? updateUser { get; set; }

        public OperationStatus? deleteUser { get; set; }

        public OperationStatus? addClass { get; set; }

        public OperationStatus? updateClass { get; set; }

        public OperationStatus? deleteClass { get; set; }
        public OperationStatus? resetPassword { get; set; }

        #endregion
    }


    public class ModelListWithData<T>
    {
        public List<T>? addWizytaDomowa { get; set; }
    }

    public class ModelListWithErrors<T>
    {
        public List<T>? errors { get; set; }

        public T? data { get; set; }
    }



    public class ModelListWithContent<T>
    {
        public string? totalPages { get; set; }
        public string? totalElements { get; set; }
        public List<T?> content { get; set; }
    }
    public class ModelWithContent<T>
    {
        public T? content { get; set; }
    }

    public class ReturnModel
    {
        public string? targetClassName { get; set; }

        public string? operationName { get; set; }

        public bool success { get; set; }

        public string? recordId { get; set; }

        public List<string>? msgList { get; set; }
    }
}
