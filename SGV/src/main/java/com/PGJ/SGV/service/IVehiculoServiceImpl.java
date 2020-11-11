package com.PGJ.SGV.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PGJ.SGV.models.dao.IVehiculoDao;
import com.PGJ.SGV.models.dao.IVehiculoDetalleDao;
import com.PGJ.SGV.models.dao.IVehiculoEstadoDao;
import com.PGJ.SGV.models.dao.IVehiculoFuncionDao;
import com.PGJ.SGV.models.dao.IVehiculoMarcaDao;
import com.PGJ.SGV.models.dao.IVehiculoTransDao;
import com.PGJ.SGV.models.entity.Vehiculo;
import com.PGJ.SGV.models.entity.VehiculoDetalle;
import com.PGJ.SGV.models.entity.VehiculoEstado;
import com.PGJ.SGV.models.entity.VehiculoFuncion;
import com.PGJ.SGV.models.entity.VehiculoMarca;
import com.PGJ.SGV.models.entity.VehiculoTransmision;

@Service
public class IVehiculoServiceImpl implements IVehiculoService {

   @Autowired
	private IVehiculoDao vehiculoDao; 
   @Autowired
	private IVehiculoDetalleDao detalleDao;   
   @Autowired
	private IVehiculoEstadoDao estadoDao;
   @Autowired
	private IVehiculoMarcaDao marcaDao;
   @Autowired
	private IVehiculoFuncionDao funcionDao;
   @Autowired
	private IVehiculoTransDao tranmicionDao;
   
	
   
    @Override
	@Transactional(readOnly = true)
	public List<Vehiculo> findAll() {
		// TODO Auto-generated method stub
    	return (List<Vehiculo>) vehiculoDao.findAll();
    	//return null;
	}

    @Override
	@Transactional
	public void save(Vehiculo vehiculo) {
		// TODO Auto-generated method stub
		vehiculoDao.save(vehiculo);
	}

    @Override
	@Transactional(readOnly = true)
	public Vehiculo findOne(Long id_vehiculo) {
		// TODO Auto-generated method stub
    	return vehiculoDao.findById(id_vehiculo).orElse(null);
    	//return null;
	}

    @Override
	@Transactional
	public void delete(Long id_vehiculo) {
		vehiculoDao.deleteById(id_vehiculo);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Vehiculo> findVehiculosArea(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return (List<Vehiculo>) vehiculoDao.findVehiculosArea(id_adscripcion);
		//return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vehiculo> findVehiculosAreaPage(Long id_adscripcion, Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoDao.findVehiculosAreaPage(id_adscripcion, pageable);
		//return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vehiculo> findAllPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vehiculo> findVehElemntoPage(String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoDao.findVehElemntoPage(elemento, pageable);
		//return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vehiculo> findVehElemAreaPage(Long id_adscripcion, String elemento, Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoDao.findVehElemAreaPage(id_adscripcion, elemento, pageable);
		//return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Long totalVehiculo() {
		// TODO Auto-generated method stub
		return vehiculoDao.TotalVehiculos();
		//return null;
	}

	@Override
	@Transactional(readOnly = true)
	public int totalVehiculoArea(Long id_adscripcion) {
		// TODO Auto-generated method stub
		return vehiculoDao.TotalVehiculosArea(id_adscripcion);
		//return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Vehiculo> findTVechiulo(String vehiculo,Pageable pageable) {
		// TODO Auto-generated method stub
		return vehiculoDao.findTVechiulo(vehiculo, pageable);
		//return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehiculoDetalle> findAllDetalle() {
		// TODO Auto-generated method stub
		return (List<VehiculoDetalle>) detalleDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehiculoEstado> findAllEstado() {
		// TODO Auto-generated method stub
		return (List<VehiculoEstado>) estadoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehiculoTransmision> findAllTransmision() {
		// TODO Auto-generated method stub
		return (List<VehiculoTransmision>) tranmicionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehiculoFuncion> findAllFuncion() {
		// TODO Auto-generated method stub
		return (List<VehiculoFuncion>) funcionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehiculoMarca> findAllMarca() {
		// TODO Auto-generated method stub
		return (List<VehiculoMarca>) marcaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findAllMarcaUnica() {
		// TODO Auto-generated method stub
		return (List<String>) marcaDao.findallMarcaUnica();
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehiculoMarca> findByMarca(String nombre_marca) {
		// TODO Auto-generated method stub
		return marcaDao.findByMarca(nombre_marca);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findallsubMarcaUnica(String nombre_marca) {
		// TODO Auto-generated method stub
		return marcaDao.findallsubMarcaUnica(nombre_marca);
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoDetalle findDetalle(Long id_detalle) {
		// TODO Auto-generated method stub
		return detalleDao.findById(id_detalle).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findallModeloUnico(String nombre_marca, String nombre_submarca) {
		// TODO Auto-generated method stub
		return marcaDao.findallModeloUnico(nombre_marca,nombre_submarca);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findallTipoUnico(String nombre_marca, String nombre_submarca, String modelo) {
		// TODO Auto-generated method stub
		return marcaDao.findallTipoUnico(nombre_marca, nombre_submarca, modelo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findallClaseUnico(String nombre_marca, String nombre_submarca, String modelo, String tipo) {
		// TODO Auto-generated method stub
		return marcaDao.findallClaseUnico(nombre_marca, nombre_submarca, modelo, tipo);
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoDetalle findByVehiculoDetalle(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return detalleDao.findByVehiculoDetalle(id_vehiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public void saveDetalle(VehiculoDetalle detalle) {
		// TODO Auto-generated method stub
		 detalleDao.save(detalle);
	}

	@Override
	@Transactional(readOnly = true)
	public String findTCDetalle(Long id_vehiculo) {
		// TODO Auto-generated method stub
		return detalleDao.findTCDetalle(id_vehiculo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> findallByClase() {
		// TODO Auto-generated method stub
		return marcaDao.findallClaseUnica();
	}

	@Override
	@Transactional(readOnly = true)
	public int ultimoId() {
		// TODO Auto-generated method stub
		return vehiculoDao.ultimoId();
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoEstado findByVehiculoEstado(Long id_estado) {
		// TODO Auto-generated method stub
		return estadoDao.findById(id_estado).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoMarca findMarca(String nombre_marca, String nombre_submarca, String modelo, String tipo,
			String clase) {
		// TODO Auto-generated method stub
		return marcaDao.findMarca(nombre_marca, nombre_submarca, modelo, tipo, clase);
	}

	@Override
	@Transactional(readOnly = true)
	public Vehiculo findByPlaca(String Placa) {
		// TODO Auto-generated method stub
		return (Vehiculo) vehiculoDao.findByPlaca(Placa);
	}

	@Override
	@Transactional(readOnly = true)
	public String findPlaca(long id_vehiculo) {
		// TODO Auto-generated method stub
		return vehiculoDao.findPlaca(id_vehiculo);
	}
	
	
	
}
