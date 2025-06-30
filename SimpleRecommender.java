import java.util.*;

public class SimpleRecommender {

    public static void main(String[] args) {
        // Sample data: user ID -> list of liked products
        Map<String, List<String>> userLikes = new HashMap<>();

        userLikes.put("user1", Arrays.asList("pen", "notebook"));
        userLikes.put("user2", Arrays.asList("pen", "marker"));
        userLikes.put("user3", Arrays.asList("notebook", "eraser"));
        userLikes.put("user4", Arrays.asList("marker", "eraser"));

        // The target user we want to recommend for
        String targetUser = "user1";

        // Get items liked by target user
        List<String> targetLikes = userLikes.get(targetUser);

        // Store recommendations
        Set<String> recommendations = new HashSet<>();

        // Loop through other users
        for (String otherUser : userLikes.keySet()) {
            if (otherUser.equals(targetUser)) continue;

            List<String> otherLikes = userLikes.get(otherUser);

            // Check for common likes
            boolean sharesInterest = false;
            for (String item : otherLikes) {
                if (targetLikes.contains(item)) {
                    sharesInterest = true;
                    break;
                }
            }

            // If users share interests, recommend new items from the other user
            if (sharesInterest) {
                for (String item : otherLikes) {
                    if (!targetLikes.contains(item)) {
                        recommendations.add(item);
                    }
                }
            }
        }

        // Show results
        System.out.println("Recommendations for " + targetUser + ": " + recommendations);
    }
}

