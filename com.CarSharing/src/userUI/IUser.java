package userUI;

import app.User;

public interface IUser {
    void bookCar(User currentUser);
    void viewCarList();
    void viewBookings(User currentUser);
    void cancelBooking(User currentUser);
}
