package service;

import java.util.List;

import domain.Camp;

public interface CampService {
	Camp findCamp(int id);
    List<Camp> findCamps(int postalCode);
}
