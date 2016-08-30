package com.llc.android_coolview.kobe.control.search;

import android.content.Context;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;

import com.llc.android_coolview.R;
import com.llc.android_coolview.kobe.bean.Station;
import com.llc.android_coolview.kobe.control.search.utils.PinyinConverter;
import com.llc.android_coolview.util.OfficeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchService extends AbstractSearchService {
	public static final String TAG = SearchService.class.getName();
	private static final String INDEX_DIR="idx";
	private static final int REBUILD_TRIGGER_THRESHOLD=50;

	private static AbstractSearchService INSTANCE =null;
	private Context context;

	private SearchService(Context context) {
		super(context.getDir(INDEX_DIR, Context.MODE_PRIVATE));
		this.context = context;
		if(indexWriter.numDocs()<REBUILD_TRIGGER_THRESHOLD){
			asyncRebuild(true);
		}
	}
	

	public static synchronized AbstractSearchService getInstance(Context context) {
		if (null == INSTANCE) {
			INSTANCE = new SearchService(context);
		}
		return INSTANCE;
	}
	
	
	@Override
	public void destroy() {
			super.destroy();
			INSTANCE = null;
			log(TAG, "destroy " + getClass().getSimpleName());
	}
	
	@Override
	public long rebuildContacts(boolean urgent) {
		List<Station> list=OfficeUtils.readExcel(context.getResources().openRawResource(R.raw.station));
		long hits = 0;
		PinyinConverter pinyinConverter = PinyinConverter.getInstance(this.context);
		Document document = new Document();
        Field nameField = createStringField(FIELD_NAME,"");
        Field pinyinField = createTextField(FIELD_PINYIN,"");
		document.add(pinyinField);
		document.add(nameField);
		try {
			indexWriter.deleteDocuments(new Term(FIELD_TYPE, INDEX_TYPE_CONTACT));
			for(int i=0;i<list.size();i++){
				hits++;
				String name=list.get(i).getName();
				String pinyin=pinyinConverter.convert(name, true);
				nameField.setStringValue(name);
				pinyinField.setStringValue(pinyin);
				indexWriter.addDocument(document);
				if (!urgent) {
					yieldInterrupt();
				}
			}
			Map<String, String> userData = new HashMap<String, String>();
			userData.put("action", "rebuild-contacts");
			userData.put("time", String.valueOf(System.currentTimeMillis()));
			indexWriter.commit(userData);
		} catch (Exception e) {
			android.util.Log.w(TAG, e.toString(), e);
		}
		return hits;
	}

	@Override
	protected void log(String tag, String msg) {
		android.util.Log.d(tag,msg);
		
	}
}
