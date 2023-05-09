package th.com.hyweb.mybuddylinebot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;


import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import lombok.extern.log4j.Log4j2;
import th.com.hyweb.mybuddylinebot.entity.LineUsers;

@Service
@Log4j2
public class LineUsersService {
	

	  public static final String COL_NAME="LineUsers";
	  public LineUsers getLineUserDetails(String name) throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(name);
	        ApiFuture<DocumentSnapshot> future = documentReference.get();

	        DocumentSnapshot document = future.get();

	        LineUsers lineuser = null;

	        if(document.exists()) {
	        	lineuser = document.toObject(LineUsers.class);
	            return lineuser;
	        }else {
	            return null;
	        }
	    }
	  
	  public List<LineUsers> getLineUserList() throws InterruptedException, ExecutionException {
		  log.debug("Test");
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        CollectionReference cities = dbFirestore.collection(COL_NAME);
	        Query query = cities.whereIn("tier", Arrays.asList("head", "leader","admin","normal","normalplus"));

	        ApiFuture<QuerySnapshot> querySnapshot = query.get();

	        List<LineUsers> lineuserlist  = new ArrayList<LineUsers>();
	        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
	        
	        	LineUsers lineuser = document.toObject(LineUsers.class);
	        	lineuserlist.add(lineuser);
	        }
	    return lineuserlist;
	    }
}
