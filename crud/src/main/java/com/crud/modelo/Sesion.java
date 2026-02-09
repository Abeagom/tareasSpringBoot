package com.crud.modelo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Sesion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "La fecha no puede estar vacía")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaHora;

    @NotBlank(message = "La modalidad es obligatoria")
    private String modalidad;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 15, message = "La sesión debe durar al menos 15 minutos")
    private Integer duracion;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(min = 3, max = 100, message = "El motivo debe tener entre 3 y 100 caracteres")
    private String motivo;

    public Sesion() {}

    public Sesion(LocalDateTime fechaHora, String modalidad, String estado, 
                  Integer duracion, String motivo, String notasEvolucion) {
        this.fechaHora = fechaHora;
        this.modalidad = modalidad;
        this.estado = estado;
        this.duracion = duracion;
        this.motivo = motivo;
    }
    
    public Sesion(LocalDateTime fechaHora, String modalidad, String estado, Integer duracion, String motivo) {
        this.fechaHora = fechaHora;
        this.modalidad = modalidad;
        this.estado = estado;
        this.duracion = duracion;
        this.motivo = motivo;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}