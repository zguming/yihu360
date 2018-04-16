package uk.co.senab.photoview.showLocalPhotoTool;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class PhotoProvider implements AbstructPhotoProvider {
	private Context mContent;

	public PhotoProvider(Context context) {
		this.mContent = context;
	}

	@Override
	public List<String> getList() {
		List<String> list = null;
		if (mContent != null) {
			Cursor cursor = mContent.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
					null, null);
			if (cursor != null) {
				list = new ArrayList<String>();
				while (cursor.moveToNext()) {
					try {
						String path = cursor
								.getString(cursor
										.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
						list.add(path);
					} catch (Exception e) {

					}
				}
				// cursor.moveToLast();
				// do {
				// String path = cursor
				// .getString(cursor
				// .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
				// list.add(path);
				// } while (cursor.moveToPrevious());
				cursor.close();
			}
		}
		return list;
	}
}
