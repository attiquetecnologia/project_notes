/*
 * Copyright (C) 2015 Rodrigo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import models.Nota;

/**
 *
 * @author Rodrigo Attique Santana
 * @version 1.0
 */
public class NotaDAO extends GenericDAO implements br.attique.utils.persistence.DefaultDAO {

    private static final String INSERT = "insert into notas (titulo,data_ocorr,texto,projeto) values "
            + "(?,?,?,?)";
    private static final String FINDBYID = "select * from notas where id = ?";
    private static final String UPDATE = "update notas set titulo = ?, data_ocorr = ?, texto = ?, projeto = ? where id = ?";
    
    @Override
    public boolean saveProcedure(Object object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean save(Object object) throws SQLException {
        Nota nota = (Nota)object;
        return executar(false
                , INSERT
                , nota.getTitulo()
                , java.sql.Timestamp.valueOf(nota.getDataOcorrencia())
                , nota.getTexto()
                , nota.getProjeto()
                );
    }

    @Override
    public boolean update(Object object) throws SQLException {
        Nota nota = (Nota)object;
        return executar(false
                , UPDATE
                , nota.getTitulo()
                , java.sql.Timestamp.valueOf(nota.getDataOcorrencia())
                , nota.getTexto()
                , nota.getProjeto()
                , nota.getId()
                );
    }

    @Override
    public boolean delete(Object object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection findByParameters(String query, Object... params) throws SQLException {
        ArrayList<Nota> notas = new ArrayList<>();
        PreparedStatement ps = getConnection().prepareStatement(query);
        
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i+1, params[i]);
        }//fim for

        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Nota n = new Nota();
            n.setId(rs.getLong("id"));
            n.setTitulo(rs.getString("titulo"));
            n.setDataOcorrencia(rs.getTimestamp("data_ocorr").toLocalDateTime());
            n.setTexto(rs.getString("texto"));
            n.setProjeto(rs.getString("projeto"));
            notas.add(n);
        }
        return notas;
    }//fim modolo

    @Override
    public boolean findParameter(Object object, String query, Object... params) throws SQLException {
        Nota nota = (Nota)object;
        ArrayList<Nota> notas = (ArrayList<Nota>) findByParameters(query, params);
        if (!notas.isEmpty()){
            nota.setId(notas.get(0).getId());
            nota.setTitulo(notas.get(0).getTitulo());
            nota.setTexto(notas.get(0).getTexto());
            nota.setProjeto(notas.get(0).getProjeto());
            nota.setDataOcorrencia(notas.get(0).getDataOcorrencia());
            return true;
        }
        return false;
    }

    @Override
    public boolean findById(Object object) throws SQLException {
        Nota nota = (Nota)object;
        return findParameter(object, FINDBYID, nota.getId());
    }//fim modolo
    
}//fim classe
