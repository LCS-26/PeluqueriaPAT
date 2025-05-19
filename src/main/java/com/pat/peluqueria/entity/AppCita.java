package com.pat.peluqueria.entity;

import com.pat.peluqueria.model.Dia;
import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "APP_CITA")
public class AppCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private AppUser cliente;

    @ManyToOne
    @JoinColumn(name = "peluquero_id", nullable = false)
    private AppUser peluquero;

    /*@Column(nullable = false)
    private LocalDateTime fecha;*/

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Dia dia;

    @Column(nullable = false)
    private String hora;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getCliente() {
        return cliente;
    }

    public void setCliente(AppUser cliente) {
        this.cliente = cliente;
    }

    public AppUser getPeluquero() {
        return peluquero;
    }

    public void setPeluquero(AppUser peluquero) {
        this.peluquero = peluquero;
    }

    /*public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }*/

    public String getDia() {
        return dia.toString();
    }
    public void setDia(Dia dia){
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }
    public void setHora(String hora){this.hora = hora;}
}

