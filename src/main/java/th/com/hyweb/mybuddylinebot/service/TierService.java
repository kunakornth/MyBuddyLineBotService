package th.com.hyweb.mybuddylinebot.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import th.com.hyweb.mybuddylinebot.entity.Tier;


@Service
public class TierService {

	 public static final String COL_NAME="Setting";
	 
	 public List<String> getTierSearch() throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document("Tier");
	        ApiFuture<DocumentSnapshot> future = documentReference.get();

	        DocumentSnapshot document = future.get();

	        Tier tier = null;
	        
	        if(document.exists()) {
	        	tier = document.toObject(Tier.class);
	            return tier.getTierUseSearch();
	        }else {
	            return null;
	        }
	    }
}
