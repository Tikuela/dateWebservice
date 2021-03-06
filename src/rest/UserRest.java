package rest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import controller.UserController;
@Path("/users")

public class UserRest { 
	
	private UserController controller = new UserController();
	
	
	@GET  
	@Produces({MediaType.APPLICATION_JSON})	
	public String findAllUsers(){
		return null; 
		//return controller.findAllOrderedByName().toString();
	} 
	
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({MediaType.APPLICATION_JSON})	
	public String getUserById(@PathParam("id") long id) throws JsonGenerationException, JsonMappingException, IOException{ 
		
		return new Gson().toJson(controller.findUserById(id)).toString();
	}
	
	@GET
	@Path("profile")	
	public String getUserProfleById(@QueryParam("sugestion") long sugestionId, @QueryParam("user") long userId, @QueryParam("accessToken") String accessToken) throws JSONException, IOException{ 
		
		return controller.getUserProfleById(sugestionId, userId, accessToken);
	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}/profile")	
	public String getUserProfleById(@PathParam("id") long id) throws JSONException{ 
		
		return controller.getUserProfleById(id);
	}
	
	
	@GET
	@Path("/{id:[0-9][0-9]*}/settings")	
	public String getUserSettings(@PathParam("id") long id) throws JSONException{ 
		
		return controller.getUserSettings(id);
	}
	
	@GET
	@Path("sugestions")	
	public String getUserSugestoinsById(@QueryParam("id") long id, @QueryParam("accessToken") String accessToken) throws JSONException, IOException{ 
		
		return controller.getUserSugestoinsById(id, accessToken).toString();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crunchifyREST(String j) throws IOException, JSONException{		
		
		  //Login login = new Gson().fromJson(j, Login[].class); 
		  
		  JSONObject json = new JSONObject(j);
		  JSONObject location =  json.getJSONObject("location");
		  if(!controller.hasUser(json.getLong("id")))
			  controller.createUserJson(json.getString("accessToken"),location, json.getString("deviceToken"), json.getString("ionicEmail"));
		  else controller.updateUser(json.getLong("id"), json.getString("accessToken"), location, json.getString("deviceToken"), json.getString("ionicEmail"));
		  return Response.status(200).build();
	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}/exists")	
	public String checkIfUserExist(@PathParam("id") long id) throws JSONException{		  
		  
		  return String.valueOf(controller.hasUser(id));  
	}	
	
	@GET
	@Path("/{id:[0-9][0-9]*}/getSocialLinks")	
	public String getSocialLinks(@PathParam("id") long id) throws JSONException{		  
		  
		  return String.valueOf(controller.getSocialLinks(id));  
	}
	
	@POST
	@Path("/update")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(String param) throws IOException, JSONException{ 
		JSONObject j = new JSONObject(param);
		String deviceToken = "";
		String ionicEmail = "";
		
		if(j.has("deviceToken"))
			deviceToken = j.getString("deviceToken"); 
		
		if(j.has("ionicEmail"))
			ionicEmail = j.getString("ionicEmail");
			
		if(controller.updateUser(j.getLong("id"), j.getString("accessToken"), j.getJSONObject("location"), deviceToken, ionicEmail))
				return Response.status(200).build();
		else return Response.status(500).build();	
		
	}
	@POST
	@Path("/create")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(String param) throws IOException, JSONException{ 
		
		JSONObject j = new JSONObject(param);
		
		String deviceToken = "";
		String ionicEmail = "";
		
		if(j.has("deviceToken"))
			deviceToken = j.getString("deviceToken"); 
		
		if(j.has("ionicEmail"))
			ionicEmail = j.getString("ionicEmail");
		
		if(controller.createUserJson(j.getString("accessToken"), j.getJSONObject("location"), deviceToken, ionicEmail))
			return Response.status(200).build();
		else return Response.status(500).build();	
		
	}
	@POST
	@Path("/updateUserSettings")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserSettings(String param) throws IOException, JSONException{ 
		JSONObject j = new JSONObject(param);
		
		if(controller.updateUserSettings(j, j.getString("accessToken")))
				return Response.status(200).build();
		else return Response.status(500).build();	
		
	}
	
	@POST
	@Path("/createSocialLink")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSocialLink(String param) throws IOException, JSONException{ 
		JSONObject j = new JSONObject(param);
		
		if(controller.createSocialLink(j))
				return Response.status(200).build();
		else return Response.status(500).build();	
		
	}
	
	@POST
	@Path("/editSocialLink")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editSocialLink(String param) throws IOException, JSONException{ 
		JSONObject j = new JSONObject(param);
		
		if(controller.editSocialLink(j))
				return Response.status(200).build();
		else return Response.status(500).build();	
		
	}

	@POST
	@Path("/deleteSocialLink")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteSocialLink(String param) throws IOException, JSONException{ 
		JSONObject j = new JSONObject(param);
		
		if(controller.deleteSocialLink(j))
				return Response.status(200).build();
		else return Response.status(500).build();	
		
	}
	
	@POST
	@Path("/updatePhoto")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePhoto(String param) throws IOException, JSONException{ 
		JSONObject j = new JSONObject(param);
		
		try {
			if(controller.updatePhoto(j))
					return Response.status(200).build();
			else return Response.status(500).build();
		} catch (Exception e) {
			return Response.status(500).build();
		}	
		
	}
	
	@POST
	@Path("/updateUserBio")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserBio(String param) throws IOException, JSONException{ 
		JSONObject j = new JSONObject(param);
		
		if(controller.updateUserBio(j))
				return Response.status(200).build();
		else return Response.status(500).build();	
		
	}
	
	@POST
	@Path("/like")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response like(String param) throws IOException, JSONException, IllegalAccessException, InvocationTargetException{ 
		JSONObject j = new JSONObject(param);
		
		if(controller.like(j))
			return Response.status(201).build();
		else return Response.status(200).build();	
		
	}
	
	@GET
	@Path("matches")	
	public String getUserMatches(@QueryParam("user") long userId, @QueryParam("accessToken") String accessToken) throws JSONException, IOException{ 
		
		return controller.getUserMatches(userId, accessToken);
	}
	
	@GET
	@Path("hasSettings")	
	public String hasSettings(@QueryParam("id") long userId, @QueryParam("accessToken") String accessToken) throws JSONException, IOException{ 
		
		return String.valueOf(controller.hasSettings(userId, accessToken));
	}
	
	@GET
	@Path("/updateAllUsers")	
	public String updateAllUsers(@QueryParam("accessToken") String accessToken) throws JSONException, IOException{		  
		  
		  return String.valueOf(controller.updateAll(accessToken));  
	}
	
} 


