package hospital.staff;

public interface LoginSystem {

private void decryptPassword()
{
    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
    String encryptedPassword = passwordEncryptor.encryptPassword(userPassword);
...
    if (passwordEncryptor.checkPassword(inputPassword, encryptedPassword)) {
        // correct!
    } else {
        // bad login!
    }
}

}
