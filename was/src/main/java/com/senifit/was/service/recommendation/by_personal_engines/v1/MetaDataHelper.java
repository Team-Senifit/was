package com.senifit.was.service.recommendation.by_personal_engines.v1;

import com.senifit.was.vo.VideoData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaDataHelper {

   public String generateThumbnailPath(List<VideoData> videoList) {
       int videoCount = videoList.size();
       int selectedIndex = videoCount / 2;
       return videoList.get(selectedIndex).thumbnailPath();
   }

   public int calculateDuration(List<VideoData> videoList) {
       int totalDuration = 0;
       for (VideoData videoData : videoList) {
           totalDuration += videoData.duration();
       }
       return totalDuration;
   }

   public int calculateDurationByMinutes(List<VideoData> videoList) {
       return calculateDuration(videoList) / 60;
   }


}
