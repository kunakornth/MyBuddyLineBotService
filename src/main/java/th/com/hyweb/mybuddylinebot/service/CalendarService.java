package th.com.hyweb.mybuddylinebot.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import th.com.hyweb.mybuddylinebot.entity.CalendarFireStore;
import th.com.hyweb.mybuddylinebot.entity.Tier;

@Service
public class CalendarService {
	
	 public static final String COL_NAME="Calendar";
	 
	 public CalendarFireStore getCalendarSearch(String year) throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(year);
	        ApiFuture<DocumentSnapshot> future = documentReference.get();

	        DocumentSnapshot document = future.get();

	        CalendarFireStore tier = null;
	        
	        if(document.exists()) {
	        	tier = document.toObject(CalendarFireStore.class);
	            return tier;
	        }else {
	            return null;
	        }
	    }
	 
	 public CalendarFireStore getMeetingSearch(String year) throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(year);
	        ApiFuture<DocumentSnapshot> future = documentReference.get();

	        DocumentSnapshot document = future.get();

	        CalendarFireStore tier = null;
	        
	        if(document.exists()) {
	        	tier = document.toObject(CalendarFireStore.class);
	            return tier;
	        }else {
	            return null;
	        }
	    }

	 
	 public void UpdateCalendar(String year,CalendarFireStore calend) throws InterruptedException, ExecutionException {
	        Firestore dbFirestore = FirestoreClient.getFirestore();
	        ApiFuture<WriteResult> future = dbFirestore.collection(COL_NAME).document(year).set(calend);
//	        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(year);
//	        ApiFuture<DocumentSnapshot> future = documentReference.get();
//
//	        DocumentSnapshot document = future.get();
//
//	        CalendarFireStore tier = null;
//	        
//	        if(document.exists()) {
//	        	tier = document.toObject(CalendarFireStore.class);
//	            return tier;
//	        }else {
//	            return null;
//	        }
	    }
}
