namespace PrzychodniaMedycznaMB_FE.GQL
{
    public static class Queries
    {
        #region Login
        public static string LoginQuery
        {
            get
            {
                return "{\"userName\":\"#USERNAME#\",\"password\":\"#PASSWORD#\"}";
            }
        }
        #endregion

        public static string QueryGetCurrentUserLocationId
        {
            get
            {
                return "query User($userId: ID!){ \n" +
                            "user(userId: $userId) \n" +
                            "{ \n" +
                                "locationId, userType, userId \n" +
                            "}\n" +
                        "}";
            }
        }

        public static string QueryGetCurrentUser
        {
            get
            {
                return "query\n" +
                        "{\n" +
                        "	currentUser{\n" +
                        "		id,email,firstName,lastName\n" +
                        "	}\n" +
                        "}";
            }
        }

        public static string QueryGetDoctorLocationEncountersQuery
        {
            get
            {
                return "query DoctorLocationEncounters($locationId: ID!, $page: Int!, $size: Int!) { \n " +
                       "encounterPageByLocationIdAndUserPatient(locationId: $locationId, page: $page, size: $size) { \n" +
                       "content {\n" +
                         "patient{\n" +
                            "firstname\n" +
                            "lastname\n" +
                          "}\n" +
                          "location {\n" +
                            "name\n" +
                            "city\n" +
                            "postalCode\n" +
                            "street\n" +
                          "}\n" +
                          "encounterId\n" +
                          "practitionerId\n" +
                          "description\n" +
                          "icd10\n" +
                          "dateFrom\n" +
                          "dateTo\n" +
                       "}\n" +
                     "}\n" +
                  "}";
            }
        }

        public static string QueryGetPatientsByLocationId
        {
            get
            {
                return "query LocationPatients($locationId: ID!, $page: Int!, $size: Int!) \n" +
                        "{ \n" +
                            "userPageByLocationIdAndUserPatient(locationId: $locationId, page: $page, size: $size) \n" +
                            "{ \n" +
                                "content \n" +
                                "{ \n" +
                                    "userId \n" +
                                    "firstname \n" +
                                    "lastname \n" +
                                    "email \n" +
                                "}\n" +
                            "}\n" +
                        "}";

            }
        }

        public static string QueryGetPractitionersByLocationId
        {
            get
            {
                return "query LocationDoctor($locationId: ID!, $page: Int!, $size: Int!) \n" +
                        "{ \n" +
                            "userPageByLocationIdAndUserDoctor(locationId: $locationId, page: $page, size: $size) \n" +
                            "{ \n" +
                                "content \n" +
                                "{ \n" +
                                    "userId \n" +
                                    "firstname \n" +
                                    "lastname \n" +
                                    "email \n" +
                                "}\n" +
                            "}\n" +
                        "}";

            }
        }


        public static string QueryGetLocations
        {
            get
            {
                return "query LocationList { \n" +
                        " locationList {\n" +
                                        "locationId\n" +
                                        "name\n" +
                                        "city\n" +
                                        "postalCode\n" +
                                        "street\n" +
                                     "}\n" +
                        "}";
            }
        }

        public static string QueryGetUsers
        {
            get
            {
                return "query UsersList { \n" +
                        " usersList {\n" +
                                        "userId \n" +
                                        "firstname \n" +
                                        "lastname \n" +
                                        "email \n" +
                                        "userType \n" +
                                        "login \n" +
                                     "}\n" +
                        "}";
            }
        }

        public static string QueryGetEncounterExaminations
        {
            get
            {
                return "query EncounterExaminations($encounterId: ID!, $page: Int!, $size: Int!) { \n" +
                        " examinationPageByEncounterId(encounterId: $encounterId, , page: $page, size: $size) { \n" +
                            "content \n" +
                                    "{ \n" +
                                        "examinationId \n" +
                                        "description \n" +
                                        "icd9 \n" +
                                        "name \n" +
                                        "executionDate \n" +
                                     "}\n" +
                               "}\n" +
                        "}";
            }
        }

        #region Mutations
                public static string OperationStatusData
                {
                    get
                    {
                        return " operationName \n" +
                               " targetClassName \n" +
                               " success \n" +
                               " recordId \n";
                    }
                }

                #region Encounter mutations

                public static string CreateEncounter
                {
                    get
                    {
                        return "mutation AddEncounter($data: EncounterInput!) { \n" +
                               "addEncounter(data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string UpdateEncounter
                {
                    get
                    {
                        return "mutation UpdateEncounter($encounterId: ID!, $data: EncounterInput!) { \n" +
                               "updateEncounter(encounterId: $encounterId, data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string DeleteEncounter
                {
                    get
                    {
                        return "mutation DeleteEncounter($encounterId: ID!) { \n" +
                               "deleteEncounter(encounterId: $encounterId) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                #endregion

                #region Location mutations

                public static string CreateLocation
                {
                    get
                    {
                        return "mutation AddLocation($data: LocationInput!) { \n" +
                               "addLocation(data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string UpdateLocation
                {
                    get
                    {
                        return "mutation UpdateLocation($locationId: ID!, $data: LocationInput!) { \n" +
                               "updateLocation(locationId: $locationId, data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string DeleteLocation
                {
                    get
                    {
                        return "mutation DeleteLocation($locationId: ID!) { \n" +
                               "deleteLocation(locationId: $locationId) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                #endregion

                #region User mutations

                public static string CreateUser
                {
                    get
                    {
                        return "mutation AddUser($data: UserInput!) { \n" +
                               "addUser(data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string UpdateUser
                {
                    get
                    {
                        return "mutation UpdateUser($userId: ID!, $data: UserInput!) { \n" +
                               "updateUser(userId: $userId, data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string DeleteUser
                {
                    get
                    {
                        return "mutation DeleteUser($userId: ID!) { \n" +
                               "deleteUser(userId: $userId) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                #endregion

                #region Class mutations

                public static string CreateClass
                {
                    get
                    {
                        return "mutation AddClass($data: ClassInput!) { \n" +
                               "addClass(data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string UpdateClass
                {
                    get
                    {
                        return "mutation UpdateClass($classId: ID!, $data: ClassInput!) { \n" +
                               "updateClass(classId: $classId, data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string DeleteClass
                {
                    get
                    {
                        return "mutation DeleteClass($classId: ID!) { \n" +
                               "deleteClass(classId: $classId) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                #endregion

                #region Session mutations

                public static string CreateSession
                {
                    get
                    {
                        return "mutation AddSession($data: SessionInput!) { \n" +
                               "addSession(data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string UpdateSession
                {
                    get
                    {
                        return "mutation UpdateSession($sessionId: ID!, $data: SessionInput!) { \n" +
                               "updateSession(sessionId: $sessionId, data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string DeleteSession
                {
                    get
                    {
                        return "mutation DeleteSession($sessionId: ID!) { \n" +
                               "deleteSession(sessionId: $sessionId) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                #endregion

                #region Permission mutations

                public static string CreatePermission
                {
                    get
                    {
                        return "mutation AddPermission($data: PermissionInput!) { \n" +
                               "addPermission(data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string UpdatePermission
                {
                    get
                    {
                        return "mutation UpdatePermission($permissionId: ID!, $data: PermissionInput!) { \n" +
                               "updatePermission(permissionId: $permissionId, data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string DeletePermission
                {
                    get
                    {
                        return "mutation DeletePermission($permissionId: ID!) { \n" +
                               "deletePermission(permissionId: $permissionId) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                #endregion

                #region Examination mutations

                public static string CreateExamination
                {
                    get
                    {
                        return "mutation AddExamination($data: ExaminationInput!) { \n" +
                               "addExamination(data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string UpdateExamination
                {
                    get
                    {
                        return "mutation UpdateExamination($examinationId: ID!, $data: ExaminationInput!) { \n" +
                               "updateExamination(examinationId: $examinationId, data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string DeleteExamination
                {
                    get
                    {
                        return "mutation DeleteExamination($examinationId: ID!) { \n" +
                               "deleteExamination(examinationId: $examinationId) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                #endregion

                #region Category mutations

                public static string CreateCategory
                {
                    get
                    {
                        return "mutation AddCategory($data: CategoryInput!) { \n" +
                               "addCategory(data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string UpdateCategory
                {
                    get
                    {
                        return "mutation UpdateCategory($categoryId: ID!, $data: CategoryInput!) { \n" +
                               "updateCategory(categoryId: $categoryId, data: $data) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

                public static string DeleteCategory
                {
                    get
                    {
                        return "mutation DeleteCategory($categoryId: ID!) { \n" +
                               "deleteCategory(categoryId: $categoryId) {\n" +
                                    OperationStatusData +
                               "  } \n" +
                               "}";
                    }
                }

        #endregion

        #region Reset password

        public static string ChangePassword
        {
            get
            {
                return "mutation resetPassword($userId: ID!, $oldPassword: String!, $newPassword: String!) { \n" +
                       "resetPassword(userId: $userId, oldPassword: $oldPassword, newPassword: $newPassword) { \n" +
                            OperationStatusData +
                       "  } \n" +
                       "}";
            }
        }

        #endregion

        #endregion
    }

}
