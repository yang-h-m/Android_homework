package com.example.minidouyin.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.minidouyin.R;
import com.example.minidouyin.activities.PlayActivity;
import com.example.minidouyin.adapter.HistoryRecyclerAdapter;
import com.example.minidouyin.db.HistoryRecord;
import com.example.minidouyin.db.MiniDouYinDatabaseHelper;
import com.example.minidouyin.db.VideoRecord;
import com.example.minidouyin.model.CurrentUser;
import com.example.minidouyin.model.Video;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment
{
	private HistoryRecyclerAdapter mHistory;

	private int mHistoryCnt = 0;

	private MiniDouYinDatabaseHelper mDBHelperHistory = new MiniDouYinDatabaseHelper(getContext());
	private MiniDouYinDatabaseHelper mDBHelperCollection = new MiniDouYinDatabaseHelper(getContext());

	private TextView mUsername_editable;
	private TextView mStudentID_editable;
	private Button mUsername_editBtn;
	private Button mStudentID_editBtn;
	private ImageButton mDelete_History_Btn;

	private List<HistoryRecord> mHistoryRecords = new ArrayList<>();

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_info, container, false);


		mUsername_editable = view.findViewById(R.id.username_editable);
		mStudentID_editable = view.findViewById(R.id.studentID_editable);

		setCurrentUserInfo();

		// set edit button
		mStudentID_editBtn = view.findViewById(R.id.btn_edit_studentID);
		mUsername_editBtn = view.findViewById(R.id.btn_edit_username);
		mDelete_History_Btn = view.findViewById(R.id.btn_delete_history);

		mStudentID_editBtn.setOnClickListener(v -> new MaterialDialog.Builder(getActivity())
				.inputType(InputType.TYPE_CLASS_TEXT)
				.input(
						"请输入你的密码",
						"",
						new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								CurrentUser.setStudentID(input.toString());
								setCurrentUserInfo();
							}
						})
				.show());

		mUsername_editBtn.setOnClickListener(v -> new MaterialDialog.Builder(getActivity())
				.inputType(InputType.TYPE_CLASS_TEXT)
				.input(
						"请输入你的用户名",
						"",
						new MaterialDialog.InputCallback() {
							@Override
							public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
								CurrentUser.setUsername(input.toString());
								setCurrentUserInfo();
							}
						})
				.show());

		mDelete_History_Btn.setOnClickListener(v -> {
			mHistoryRecords.clear();
			refreshData();
		});


		mHistory = new HistoryRecyclerAdapter(R.layout.layout_history);

		final List<Video> historyVideos = new ArrayList<>();
		mDBHelperHistory.setOnGetVideoByIdListener(videoRecord -> {
			historyVideos.add(videoRecord.getVideo());
			mHistoryCnt --;

			if(mHistoryCnt == 0)
				mHistory.setNewData(historyVideos);
		});
		mDBHelperHistory.setOnGetHistoryByStudentIdListener(historyRecords -> {
			mHistoryCnt = historyRecords.size();
			mHistoryRecords = historyRecords;
			historyVideos.clear();
			if(historyRecords.isEmpty())
				mHistory.setNewData(new ArrayList<>());
			else for(HistoryRecord record : historyRecords)
			{
				mDBHelperHistory.executeGetVideoById(record.getVideoId());
			}
		});

		mHistory.setOnItemClickListener((adapter, view1, position) -> PlayActivity.launch((Activity) getContext(), mHistory.getData(), position));
		mHistory.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
		mHistory.isFirstOnly(false);
		mHistory.setEmptyView(R.layout.layout_nohistory, container);

		return view;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		refreshData();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mDBHelperCollection.cancelAllAsyncTasks();
		mDBHelperHistory.cancelAllAsyncTasks();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && isVisible()) {
			refreshData();
		}
	}

	private void setCurrentUserInfo()
	{
		mUsername_editable.setText(CurrentUser.getUsername());
		mStudentID_editable.setText(CurrentUser.getStudentID());
	}

	private void refreshData()
	{
		mDBHelperHistory.executeGetHistoryByStudentId(CurrentUser.getStudentID());
		mDBHelperCollection.executeGetCollectionByStudentId(CurrentUser.getStudentID());
	}
}