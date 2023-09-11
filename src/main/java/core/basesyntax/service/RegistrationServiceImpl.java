package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final String TYPE_LOGIN = "Login";
    private static final String TYPE_PASSWORD = "Password";
    private static final int DATA_CORRECT_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private StorageDaoImpl storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user == null) {
            throw new FailedRegistrationException("User can't be null");
        }
        checkLogin(user);
        checkPassword(user);
        checkAge(user);
        return storageDao.add(user);
    }

    private void checkAge(User user) {
        if (user.getAge() < MINIMUM_AGE) {
            throw new FailedRegistrationException("Invalid age, "
                    + "it must be at least " + MINIMUM_AGE);
        }
    }

    private void checkPassword(User user) {
        checkData(user.getPassword(), TYPE_PASSWORD);
    }

    private void checkLogin(User user) {
        checkData(user.getLogin(), TYPE_LOGIN);
        if (storageDao.get(user.getLogin()) != null) {
            throw new FailedRegistrationException("The user with login "
                    + user.getLogin() + " already exists.");
        }
    }

    private void checkData(String test, String type) {
        if (test == null) {
            throw new FailedRegistrationException(type + " can't be null");
        }
        if (test.length() < DATA_CORRECT_LENGTH) {
            throw new FailedRegistrationException(type + " can't be so short."
                    + System.lineSeparator() + "Correct length at least " + DATA_CORRECT_LENGTH
                    + ".Actual length is " + test.length());
        }
        if (test.isBlank()) {
            throw new FailedRegistrationException(type + " can't be blank.");
        }
    }
}
