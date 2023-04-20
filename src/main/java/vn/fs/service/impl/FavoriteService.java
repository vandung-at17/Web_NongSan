package vn.fs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.fs.repository.FavoriteRepository;
import vn.fs.service.IFavoriteService;

@Service
public class FavoriteService implements IFavoriteService{

	@Autowired
	private FavoriteRepository favoriteRepository;
	
	@Override
	public Integer selectCountFavoriteSave(long userID) {
		// TODO Auto-generated method stub
		Integer totalFavorite = favoriteRepository.selectCountSave(userID);
		return totalFavorite;
	}

}
