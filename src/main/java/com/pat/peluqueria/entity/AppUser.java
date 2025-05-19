package com.pat.peluqueria.entity;

import com.pat.peluqueria.model.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "APP_USER", uniqueConstraints = {
@UniqueConstraint(columnNames = "email")
})
public class AppUser implements UserDetails{

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // o lógica personalizada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // o lógica personalizada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // o lógica personalizada
    }

    @Override
    public boolean isEnabled() {
        return true; // o lógica personalizada
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "cliente")
    private List<AppCita> citasComoCliente = new ArrayList<>();

    @OneToMany(mappedBy = "peluquero")
    private List<AppCita> citasComoPeluquero = new ArrayList<>();


    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }
}