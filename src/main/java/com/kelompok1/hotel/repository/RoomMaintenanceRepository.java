package com.kelompok1.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.kelompok1.hotel.model.RoomMaintenance;
import java.util.List;
import com.kelompok1.hotel.enums.RoomStatus;

@Repository
public interface RoomMaintenanceRepository extends JpaRepository<RoomMaintenance, Integer> {
    List<RoomMaintenance> findByStatus(RoomStatus status);

    @Query(value = "SELECT * FROM `rooms` WHERE `status` = ?1 OR `room_id` = ?2", nativeQuery = true)
    List<RoomMaintenance> findByStatusExcept(String status, Integer id);
}
