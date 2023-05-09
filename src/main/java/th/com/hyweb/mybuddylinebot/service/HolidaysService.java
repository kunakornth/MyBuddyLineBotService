package th.com.hyweb.mybuddylinebot.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import lombok.extern.log4j.Log4j2;
import th.com.hyweb.mybuddylinebot.entity.CalendarFireStore;
import th.com.hyweb.mybuddylinebot.entity.Holiday;

@Service
@Log4j2
public class HolidaysService {

	public static final String COL_NAME="Holidays";
	 public Holiday getHolidaySearch(String year) throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(year);
	        ApiFuture<DocumentSnapshot> future = documentReference.get();

	        DocumentSnapshot document = future.get();

	        Holiday tier = null;
	        
	        if(document.exists()) {
	        	tier = document.toObject(Holiday.class);
	            return tier;
	        }else {
	            return null;
	        }
	    }

	 public void UpdateCalendar(String year,Holiday holi) throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        ApiFuture<WriteResult> future = dbFirestore.collection(COL_NAME).document(year).set(holi);
	 
	 }
}
