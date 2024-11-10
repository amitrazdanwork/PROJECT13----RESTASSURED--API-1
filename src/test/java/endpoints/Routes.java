package endpoints;

public class Routes {

	private static String base_url = "https://petstore.swagger.io/v2";

	// User module URLs
	static String post_url = base_url + "/user";
	static String get_url = base_url + "/user/{username}";
	static String update_url = base_url + "/user/{username}";
	static String delete_url = base_url + "/user/{username}";

}
