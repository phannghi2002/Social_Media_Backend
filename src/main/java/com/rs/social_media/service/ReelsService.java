package com.rs.social_media.service;

import com.rs.social_media.model.Reels;
import com.rs.social_media.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface ReelsService {
    public Reels createReel (Reels reel, User user);

    public List<Reels> findAllReels();

    public List<Reels> findUsersReel(Integer userId) throws Exception;


}
