package com.example.yiliaoyinian.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.yiliaoyinian.R;


/**
 * 视频等级
 */
public enum VideoLevel {
    /**
     * 标清
     */
    SD {
        @Override
        public String getName(@NonNull Context context) {
            return context.getString(R.string.videoLevel_sd);
        }
    },
    /**
     * 省流量
     */
    EC {
        @Override
        public String getName(@NonNull Context context) {
            return context.getString(R.string.videoLevel_ec);
        }
    },
    /**
     * 高清
     */
    HD {
        @Override
        public String getName(@NonNull Context context) {
            return context.getString(R.string.videoLevel_hd);
        }
    },
    /**
     * 超清
     */
    FHD {
        @Override
        public String getName(@NonNull Context context) {
            return context.getString(R.string.videoLevel_fhd);
        }
    };

    public abstract String getName(@NonNull Context context);
}
