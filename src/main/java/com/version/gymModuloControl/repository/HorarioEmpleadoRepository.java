package com.version.gymModuloControl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.version.gymModuloControl.dto.HorarioEmpleadoInfoDTO;
import com.version.gymModuloControl.dto.HorarioInstructorDTO;
import com.version.gymModuloControl.model.HorarioEmpleado;
import com.version.gymModuloControl.model.TipoInstructor;

public interface HorarioEmpleadoRepository extends JpaRepository<HorarioEmpleado, Integer> {
    @Query("SELECT new com.version.gymModuloControl.dto.HorarioEmpleadoInfoDTO(" +
            "h.idHorarioEmpleado, " +
            "p.nombre, " +
            "p.apellidos, " +
            "CASE " +
            "  WHEN e.tipoInstructor IS NOT NULL THEN 'ENTRENADOR' " +
            "  ELSE COALESCE(r.nombre, 'EMPLEADO') " +
            "END, " +
            "h.dia, " +
            "h.horaInicio, " +
            "h.horaFin, " +
            "h.turno, " +
            "h.estado) " +
            "FROM HorarioEmpleado h " +
            "JOIN h.empleado e " +
            "JOIN e.persona p " +
            "LEFT JOIN e.persona.usuario u " +
            "LEFT JOIN u.usuarioRoles ur " +
            "LEFT JOIN ur.rol r " +
            "WHERE (e.tipoInstructor IS NOT NULL) OR (u IS NOT NULL)")
    List<HorarioEmpleadoInfoDTO> obtenerInfoHorariosEmpleados();

    @Query("SELECT h FROM HorarioEmpleado h " +
            "WHERE h.empleado.idEmpleado = :idEmpleado " +
            "AND h.dia = :dia " +
            "AND h.estado = true " +
            "ORDER BY h.horaInicio ASC")
    List<HorarioEmpleado> findByEmpleadoAndDia(@Param("idEmpleado") Long idEmpleado, @Param("dia") String dia);

    List<HorarioEmpleado> findByEmpleadoIdEmpleadoAndEstadoTrue(Integer idEmpleado);

    @Query("SELECT new com.version.gymModuloControl.dto.HorarioInstructorDTO(" +
            "h.idHorarioEmpleado, h.dia, h.horaInicio, h.horaFin, h.turno) " +
            "FROM HorarioEmpleado h " +
            "JOIN h.empleado e " +
            "JOIN e.persona p " +
            "JOIN p.usuario u " +
            "JOIN u.usuarioRoles ur " +
            "JOIN ur.rol r " +
            "WHERE e.idEmpleado = :idEmpleado " +
            "AND h.estado = true " +
            "AND r.nombre = 'ENTRENADOR' " +
            "ORDER BY " +
            "CASE h.dia " +
            "WHEN 'LUNES' THEN 1 " +
            "WHEN 'MARTES' THEN 2 " +
            "WHEN 'MIERCOLES' THEN 3 " +
            "WHEN 'JUEVES' THEN 4 " +
            "WHEN 'VIERNES' THEN 5 " +
            "WHEN 'SABADO' THEN 6 " +
            "WHEN 'DOMINGO' THEN 7 " +
            "ELSE 8 END, h.horaInicio")
    List<HorarioInstructorDTO> listarHorariosPorInstructor(@Param("idEmpleado") Integer idEmpleado);

    List<HorarioEmpleado> findByEmpleadoTipoInstructorAndEstadoTrue(TipoInstructor tipoInstructor);


}