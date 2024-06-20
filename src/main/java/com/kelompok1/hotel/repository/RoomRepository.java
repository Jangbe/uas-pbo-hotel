package com.kelompok1.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kelompok1.hotel.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
