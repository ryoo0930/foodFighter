package kr.ac.jbnu.se.foodfighter;

public class RestaurantInfo {

    public class user {

        public String restaurantName;

        public user() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public user(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getUserName() {
            return restaurantName;
        }

        public void setUserName(String restaurantName) {
            this.restaurantName = restaurantName;
        }


        @Override
        public String toString() {
            return "user{" +
                    "userName ='" + restaurantName + '\'' +
                    '}';
        }
    }
}
