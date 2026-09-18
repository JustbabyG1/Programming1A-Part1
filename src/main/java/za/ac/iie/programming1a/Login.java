package za.ac.iie.programming1a;

public class Login {


        private String username;
        private String password;
        private String cellphoneNumber;
        private String firstName;
        private String lastName;

        public Login(String username, String password,
                     String cellphoneNumber, String firstName, String lastName) {

            this.username = username;
            this.password = password;
            this.cellphoneNumber = cellphoneNumber;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public boolean checkUserName() {

            return username.contains("_") && username.length() <= 5;
        }

        public boolean checkPasswordComplexity() {

            boolean hasEightCharacters = password.length() >= 8;
            boolean hasCapitalLetter = password.matches(".*[A-Z].*");
            boolean hasNumber = password.matches(".*[0-9].*");
            boolean hasSpecialCharacter = password.matches(".*[^a-zA-Z0-9].*");

            return hasEightCharacters
                    && hasCapitalLetter
                    && hasNumber
                    && hasSpecialCharacter;
        }

        public boolean checkCellPhoneNumber() {

            String phoneRegex = "^\\+\\d{1,3}\\d{1,10}$";

            return cellphoneNumber.matches(phoneRegex);
        }

        public String registerUser() {

            if (!checkUserName()) {
                return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
            }

            if (!checkPasswordComplexity()) {
                return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
            }

            if (!checkCellPhoneNumber()) {
                return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
            }

            return "Registration successful.";
        }

        public boolean loginUser(String enteredUsername, String enteredPassword) {

            return username.equals(enteredUsername)
                    && password.equals(enteredPassword);
        }

        public String returnLoginStatus(boolean loginStatus) {

            if (loginStatus) {
                return "Welcome " + firstName + " " + lastName
                        + ", it is great to see you again.";
            }

            return "Username or password incorrect, please try again.";
        }
    }