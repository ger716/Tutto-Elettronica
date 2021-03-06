package it.unisa.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import it.unisa.bean.ProdottoBean;
import it.unisa.bean.ProdottoInMagazzinoBean;
import it.unisa.bean.ProdottoInRiparazioneBean;
import it.unisa.bean.ProdottoPrenotatoBean;


public class ProdottoManagerDM implements ProdottoManager<ProdottoBean> {

	public ProdottoManagerDM() {
		
	}

	
	@Override
	public Collection<ProdottoInMagazzinoBean> doRetrieveAll() throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		Collection<ProdottoInMagazzinoBean> products=new ArrayList<ProdottoInMagazzinoBean>();
		
		String selectSQL="SELECT * FROM PRODOTTO AS P, PRODOTTOINMAGAZZINO AS M WHERE P.CODICE=M.IDPM";
		
		try {
			
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(selectSQL);
			
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()) {
				ProdottoInMagazzinoBean bean=new ProdottoInMagazzinoBean();
				bean.setIdProdotto(rs.getInt("CODICE"));
				bean.setCosto(Double.parseDouble(rs.getString("PREZZO")));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setImmagine(rs.getString("IMMAGINE"));
				bean.setTipo(rs.getString("CATEGORIA"));
				bean.setNome(rs.getString("NOME"));
				bean.setMarca(rs.getString("MARCA"));
				bean.setPromo(rs.getBoolean("PROMO"));
				bean.setQuantitaNelCarrello(rs.getInt("QUANTITANELCARRELLO"));
				bean.setQuantitaInMagazzino(rs.getInt("QUANTITAINMAGAZZINO"));
				products.add(bean);
			}
		}finally {
			try {
				if(preparedStatement!=null)
					preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return products;
	}
	
	@Override
	public ProdottoBean doRetrieveByKey(int code, String tipoProdotto) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		
		if(tipoProdotto.equalsIgnoreCase("prodottoinmagazzino")){
			String selectSQL="SELECT * FROM PRODOTTOINMAGAZZINO AS M, PRODOTTO AS P WHERE M.IDPM= ? && P.CODICE=M.IDPM";
			
			try {
				
				connection=DriverManagerConnectionPool.getConnection();
				preparedStatement=connection.prepareStatement(selectSQL);
				preparedStatement.setInt(1, code);
				
				
				ResultSet rs=preparedStatement.executeQuery();
				ProdottoInMagazzinoBean bean=new ProdottoInMagazzinoBean();
				while(rs.next()) {
					bean.setIdProdotto(rs.getInt("CODICE"));
					bean.setCosto(Double.parseDouble(rs.getString("PREZZO")));
					bean.setDescrizione(rs.getString("DESCRIZIONE"));
					bean.setImmagine(rs.getString("IMMAGINE"));
					bean.setTipo(rs.getString("CATEGORIA"));
					bean.setNome(rs.getString("NOME"));
					bean.setMarca(rs.getString("MARCA"));
					bean.setPromo(rs.getBoolean("PROMO"));
					bean.setQuantitaNelCarrello(rs.getInt("QUANTITANELCARRELLO"));
					bean.setQuantitaInMagazzino(rs.getInt("QUANTITAINMAGAZZINO"));
					return bean;
					
				}
			}finally {
				try {
					if(preparedStatement!=null)
						preparedStatement.close();
				}finally {
					DriverManagerConnectionPool.releaseConnection(connection);
				}
			}
		}else
			if(tipoProdotto.equalsIgnoreCase("prodottoprenotato")) {
				String selectSQL="SELECT * FROM PRODOTTOPRENOTATO AS PP, PRODOTTO AS P WHERE PP.IDPRENOTAZIONEPRODOTTO= ? && P.CODICE=PP.IDPRENOTAZIONEPRODOTTO";
				
				try {
					
					connection=DriverManagerConnectionPool.getConnection();
					preparedStatement=connection.prepareStatement(selectSQL);
					preparedStatement.setInt(1, code);
					
					
					ResultSet rs=preparedStatement.executeQuery();
					ProdottoPrenotatoBean bean = new ProdottoPrenotatoBean();
					while(rs.next()) {
						bean.setIdProdotto(rs.getInt("CODICE"));
						bean.setCosto(Double.parseDouble(rs.getString("PREZZO")));
						bean.setDescrizione(rs.getString("DESCRIZIONE"));
						bean.setImmagine(rs.getString("IMMAGINE"));
						bean.setTipo(rs.getString("CATEGORIA"));
						bean.setNome(rs.getString("NOME"));
						bean.setMarca(rs.getString("MARCA"));
						bean.setQuantitaPrenotata(rs.getInt("QUANTITAPRENOTATA"));
						bean.setIdPrenotazioneProdotto(rs.getInt("IDPRENOTAZIONEPRODOTTO"));
						bean.setCodiceCliente(rs.getString("CFCLIENTE"));
						bean.setDataPrenotazione(rs.getDate("DATAPRENOTAZIONE"));
						return bean;
					}
				}finally {
					try {
						if(preparedStatement!=null)
							preparedStatement.close();
					}finally {
						DriverManagerConnectionPool.releaseConnection(connection);
					}
				}
			}else
				if(tipoProdotto.equalsIgnoreCase("prodottoinriparazione")) {
					String selectSQL="SELECT * FROM PRODOTTO AS P, PRODOTTOINRIPARAZIONE AS R WHERE R.IDPR= ? && P.CODICE=R.IDPR";
					
					try {
						
						connection=DriverManagerConnectionPool.getConnection();
						preparedStatement=connection.prepareStatement(selectSQL);
						preparedStatement.setInt(1, code);
						
						
						ResultSet rs=preparedStatement.executeQuery();
						ProdottoInRiparazioneBean bean=new ProdottoInRiparazioneBean();
						while(rs.next()) {
							bean.setIdProdotto(rs.getInt("CODICE"));
							bean.setCosto(Double.parseDouble(rs.getString("PREZZO")));
							bean.setDescrizione(rs.getString("DESCRIZIONE"));
							bean.setImmagine(rs.getString("IMMAGINE"));
							bean.setTipo(rs.getString("CATEGORIA"));
							bean.setNome(rs.getString("NOME"));
							bean.setMarca(rs.getString("MARCA"));
							bean.setIdPrenotazione(rs.getInt("IDPRENOTAZIONERIPARAZIONE"));
							bean.setDataIncontro(rs.getDate("dataincontro"));
							bean.setCodiceCliente(rs.getString("CFCLIENTE"));
							bean.setStatoRiparazione(rs.getString("STATORIPARAZIONE"));
							bean.setDescrizioneProblema(rs.getString("DESCRIZIONEPROBLEMA"));
							return bean;
						}
					}finally {
						try {
							if(preparedStatement!=null)
								preparedStatement.close();
						}finally {
							DriverManagerConnectionPool.releaseConnection(connection);
						}
					}
				}
		return null;
	}

/*	
	@Override
	public void doSave(ProdottoBean product) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO MERCE" 
				+ " (CODICE, NOME, TIPO, PREZZO, QUANTITA, MARCA, DESCRIZIONE, IMMAGINE, PROMO, TIPOLOGIAPRODOTTO) VALUES (?, ?,?, ?, ?, ?, ?, ?)";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, product.getCodice());
			preparedStatement.setString(2, product.getNome());
			preparedStatement.setString(3, product.getTipo());
			preparedStatement.setDouble(4, product.getCosto());
			preparedStatement.setDouble(5, product.getCosto_prom());
			preparedStatement.setString(6, product.getFormato());
			preparedStatement.setString(7, product.getDescrizione());
			preparedStatement.setString(8, product.getUrl());
			preparedStatement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}
*/
	
	@Override
	public boolean doUpdateQuantitaNelCarrello(int codice, int quantita) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE prodottoinmagazzino SET quantitanelcarrello = ? WHERE idpm = ?";
		

		try {
			
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setInt(1, quantita);
			preparedStatement.setInt(2, codice);
			
			preparedStatement.executeUpdate();
			connection.commit();
			
			return true;
		}catch(Exception e) {return false;} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}
	
	@Override
	public boolean doUpdateQuantitaInMagazzino(int codice, int quantita) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE prodottoinmagazzino SET quantitainmagazzino = ? WHERE idpm = ?";
		

		try {
			
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setInt(1, quantita);
			preparedStatement.setInt(2, codice);
			
			preparedStatement.executeUpdate();
			connection.commit();
			
			return true;
		}catch(Exception e) {return false;} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
	}


	@Override
	public ArrayList<ProdottoBean> doRetrieveCategoria(String parola) throws SQLException {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ArrayList<ProdottoBean> products=new ArrayList<ProdottoBean>();
		
		String selectSQL="SELECT * FROM PRODOTTOINMAGAZZINO AS M, PRODOTTO AS P WHERE P.CATEGORIA=? && M.IDPM=P.CODICE";
		
		try {
			
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, parola);
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next()) {
				ProdottoInMagazzinoBean bean=new ProdottoInMagazzinoBean();
				bean.setIdProdotto(rs.getInt("CODICE"));
				bean.setCosto(Double.parseDouble(rs.getString("PREZZO")));
				bean.setDescrizione(rs.getString("DESCRIZIONE"));
				bean.setImmagine(rs.getString("IMMAGINE"));
				bean.setTipo(rs.getString("CATEGORIA"));
				bean.setNome(rs.getString("NOME"));
				bean.setMarca(rs.getString("MARCA"));
				bean.setQuantitaNelCarrello(rs.getInt("QUANTITANELCARRELLO"));
				bean.setPromo(rs.getBoolean("PROMO"));
				bean.setQuantitaInMagazzino(rs.getInt("QUANTITAINMAGAZZINO"));
				products.add(bean);
			}
		}finally {
			try {
				if(preparedStatement!=null)
					preparedStatement.close();
			}finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		
		return products;
	}
public boolean doSaveInMagazzino(ProdottoBean prodotto, boolean promo, int quantita) throws SQLException{
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	String insertSQL = "INSERT INTO PRODOTTOINMAGAZZINO (IDPM,PROMO,QUANTITAINMAGAZZINO,QUANTITANELCARRELLO) VALUES (?,?,?,?)";
	
	try {
		connection = DriverManagerConnectionPool.getConnection();
		preparedStatement = connection.prepareStatement(insertSQL);
		preparedStatement.setInt(1, prodotto.getIdProdotto());
		preparedStatement.setBoolean(2, promo);
		preparedStatement.setInt(3, quantita);
		preparedStatement.setInt(4, 0);
	
		preparedStatement.executeUpdate();
		connection.commit();
		
		return true;
	}catch(Exception e){
		return false;}
	finally {
		try {
			if(preparedStatement!=null)
				preparedStatement.close();
		}finally {
			DriverManagerConnectionPool.releaseConnection(connection);
		}
	}
}

@Override
public void doSave(ProdottoBean prodotto) throws SQLException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	String insertSQL = "INSERT INTO PRODOTTO (nome,categoria,prezzo,marca,descrizione,immagine) VALUES (?,?,?,?,?,?)";
	
	try {
		connection = DriverManagerConnectionPool.getConnection();
		preparedStatement = connection.prepareStatement(insertSQL);

		preparedStatement.setString(1, prodotto.getNome());
		preparedStatement.setString(2, prodotto.getTipo());
		preparedStatement.setDouble(3, prodotto.getCosto());
		preparedStatement.setString(4, prodotto.getMarca());
		preparedStatement.setString(5, prodotto.getDescrizione());
		preparedStatement.setString(6, prodotto.getImmagine());
	
		preparedStatement.executeUpdate();
		connection.commit();
	}catch(Exception e){
		try {
			if(preparedStatement!=null)
				preparedStatement.close();
		}finally {
			DriverManagerConnectionPool.releaseConnection(connection);
		}
	}
}


@Override
public boolean doDelete(int code) throws SQLException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	PreparedStatement preparedStatement2 = null;
	
	String deleteSQL = "DELETE FROM PRODOTTO WHERE CODICE = ?";
	String deleteSQL1 = "DELETE FROM PRODOTTOINMAGAZZINO WHERE IDPM = ?";
	
	try {
		
		connection=DriverManagerConnectionPool.getConnection();
		
		preparedStatement2=connection.prepareStatement(deleteSQL1);
		preparedStatement2.setInt(1, code);
		preparedStatement2.executeUpdate();
		
		preparedStatement=connection.prepareStatement(deleteSQL);
		preparedStatement.setInt(1, code);
		preparedStatement.executeUpdate();
		
		connection.commit();
		
		return true;
	}catch(Exception e){
		return false;
	}finally {
		try {
			if(preparedStatement!=null)
				preparedStatement.close();
		}finally {
			DriverManagerConnectionPool.releaseConnection(connection);
		}
	}
}


@Override
public int doRetrieveLastKey() throws SQLException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	String selectSQL ="SELECT MAX(CODICE) FROM PRODOTTO";
	int lastCodice = 0;
	
	try {
		connection = DriverManagerConnectionPool.getConnection();
		preparedStatement = connection.prepareStatement(selectSQL);
		
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()) {
			lastCodice = rs.getInt("max(codice)");	
		}
	}catch(Exception e){
		try {
			if(preparedStatement!=null)
				preparedStatement.close();
		}finally {
			DriverManagerConnectionPool.releaseConnection(connection);
		}
	}
	
	return lastCodice;
}


@Override
public Collection<ProdottoInMagazzinoBean> doRetrieveOnSale() throws SQLException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	
	String selectSQL = "SELECT * FROM PRODOTTOINMAGAZZINO AS PM, PRODOTTO AS P WHERE P.CODICE = IDPM && PROMO = TRUE";
	
	Collection<ProdottoInMagazzinoBean> prodottiOnSale = new ArrayList<ProdottoInMagazzinoBean>();
	
	try {
		connection = DriverManagerConnectionPool.getConnection();
		preparedStatement = connection.prepareStatement(selectSQL);
		
		ResultSet rs=preparedStatement.executeQuery();
		while(rs.next()) {
			ProdottoInMagazzinoBean bean = new ProdottoInMagazzinoBean();
			bean.setCosto(rs.getDouble("prezzo"));
			bean.setDescrizione(rs.getString("descrizione"));
			bean.setIdProdotto(rs.getInt("idpm"));
			bean.setImmagine(rs.getString("immagine"));
			bean.setMarca(rs.getString("marca"));
			bean.setNome(rs.getString("nome"));
			bean.setPromo(rs.getBoolean("promo"));
			bean.setQuantitaInMagazzino(rs.getInt("quantitainmagazzino"));
			bean.setQuantitaNelCarrello(rs.getInt("quantitanelcarrello"));
			bean.setTipo(rs.getString("categoria"));
			prodottiOnSale.add(bean);
		}
	}catch(Exception e){
		try {
			if(preparedStatement!=null)
				preparedStatement.close();
		}finally {
			DriverManagerConnectionPool.releaseConnection(connection);
		}
	}
	return prodottiOnSale;
}


@Override
public boolean doUpdatePromo(int codice, String tipo) throws SQLException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;

	String updateSQL = ""; 
	
	if(tipo.equals("add")) {
		try {
			updateSQL = "UPDATE prodottoinmagazzino SET promo = ? WHERE idpm = ?";
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setInt(2, codice);
			
			preparedStatement.executeUpdate();
			connection.commit();
			
			return true;
		}catch(Exception e) {return false;} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}else {
		try {
			updateSQL = "UPDATE prodottoinmagazzino SET promo = ? WHERE idpm = ?";
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setBoolean(1, false);
			preparedStatement.setInt(2, codice);
			
			preparedStatement.executeUpdate();
			connection.commit();
			
			return true;
		}catch(Exception e) {return false;} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
}

public boolean doUpdatePrezzo(int codice, double nuovoPrezzo) throws SQLException {
	Connection connection = null;
	PreparedStatement preparedStatement = null;

	String updateSQL = "UPDATE prodotto SET prezzo = ? WHERE codice = ?"; 
	
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(updateSQL);
			preparedStatement.setDouble(1, nuovoPrezzo);
			preparedStatement.setInt(2, codice);
			
			preparedStatement.executeUpdate();
			connection.commit();
			
			return true;
		}catch(Exception e) {return false;} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
}

}



