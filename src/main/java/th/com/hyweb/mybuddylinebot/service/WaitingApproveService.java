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
import th.com.hyweb.mybuddylinebot.entity.WaitingApprove;

@Log4j2
@Service
public class WaitingApproveService {

	 public static final String COL_NAME="WaitingApprove";
	 public List<WaitingApprove> getWaitingApproveSearch() throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        CollectionReference cities = dbFirestore.collection(COL_NAME);
	        Query query = cities.whereIn("tieruser", Arrays.asList("head", "leader","admin","normal","normalplus"));

	        ApiFuture<QuerySnapshot> querySnapshot = query.get();

	        List<WaitingApprove> waitingApprove = new ArrayList<WaitingApprove>();
	        
	        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
		        
	        	WaitingApprove lineuser = document.toObject(WaitingApprove.class);
	        	waitingApprove.add(lineuser);
//	        	log.debug(lineuser);
	        }
	      
	        return waitingApprove;
	    }
}
