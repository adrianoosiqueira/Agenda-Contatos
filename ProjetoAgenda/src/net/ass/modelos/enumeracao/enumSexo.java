/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package net.ass.modelos.enumeracao;

/**
 *
 * @author adria
 */
public enum enumSexo {
    MASCULINO("MASCULINO"), FEMININO("FEMININO");
    
    private String descricao;
    
    enumSexo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    // Método estático para converter uma String em enumSexo
    public static enumSexo fromString(String descricao) throws IllegalArgumentException {
        for (enumSexo sexo : enumSexo.values()) {
            if (sexo.getDescricao().equalsIgnoreCase(descricao)) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Sexo inválido: " + descricao);
    }
}

