package com.example.minidouyin.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "history", primaryKeys = {"stuid", "video_id"})
public class HistoryRecord {

	@NonNull
	@ColumnInfo(name = "stuid")
	private String mStudentId;

	@NonNull
	@ColumnInfo(name = "video_id")
	private String mVideoId;

	@ColumnInfo(name = "time")
	private String mTime;

	public HistoryRecord(String studentId, String videoId, String time) {
		mStudentId = studentId;
		mVideoId = videoId;
		mTime = time;
	}

	public String getStudentId() {
		return mStudentId;
	}
	public void setStudentId(String studentId) {
		this.mStudentId = studentId;
	}
	public String getTime() {
		return mTime;
	}
	public String getVideoId() {
		return mVideoId;
	}
}
