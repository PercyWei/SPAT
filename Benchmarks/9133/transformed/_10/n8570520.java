class n8570520 {
	public String doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		t_information_EditMap editMap = new t_information_EditMap();
		if (logger.isDebugEnabled()) {
			logger.debug("doAdd(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse) - start");
		}
		try {
			t_information_Form vo = null;
			vo = (t_information_Form) form;
			vo.setCompany(vo.getCounty());
			String str_postFIX = "";
			if ("????".equals(vo.getInfo_type())) {
				vo.setInfo_level(null);
				vo.setAlert_level(null);
			}
			int i_p = 0;
			editMap.add(vo);
			try {
				logger.info("???????�l??");
				String[] mobiles = request.getParameterValues("mobiles");
				vo.setMobiles(mobiles);
				SMSService.inforAlert(vo);
			} catch (Exception e) {
				logger.error("doAdd(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)", e);
			}
			String filename = vo.getFile().getFileName();
			if (null != filename && !"".equals(filename)) {
				FormFile file = vo.getFile();
				String realpath = getServlet().getServletContext().getRealPath("/");
				String inforId = vo.getId();
				realpath = realpath.replaceAll("\\\\", "/");
				String rootFilePath = getServlet().getServletContext().getRealPath(request.getContextPath());
				rootFilePath = (new StringBuilder(String.valueOf(rootFilePath))).append(UploadFileOne.strPath)
						.toString();
				String strAppend = (new StringBuilder(String.valueOf(UUIDGenerator.nextHex())))
						.append(UploadFileOne.getFileType(file)).toString();
				if (file.getFileSize() != 0) {
					file.getInputStream();
					String name = file.getFileName();
					i_p = file.getFileName().lastIndexOf(".");
					str_postFIX = file.getFileName().substring(i_p, file.getFileName().length());
					t_attach attach = new t_attach();
					String fullPath = realpath + "attach/" + strAppend + str_postFIX;
					attach.setAttach_fullname(fullPath);
					attach.setAttach_name(name);
					attach.setInfor_id(Integer.parseInt(inforId));
					attach.setInsert_day(new Date());
					attach.setUpdate_day(new Date());
					t_attach_EditMap attachEdit = new t_attach_EditMap();
					attachEdit.add(attach);
					File sysfile = new File(fullPath);
					if (!sysfile.exists()) {
						sysfile.createNewFile();
					}
					java.io.OutputStream out = new FileOutputStream(sysfile);
					org.apache.commons.io.IOUtils.copy(file.getInputStream(), out);
					out.close();
				}
			}
		} catch (HibernateException e) {
			logger.error("doAdd(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)", e);
			ActionErrors errors = new ActionErrors();
			errors.add("org.apache.struts.action.GLOBAL_ERROR", new ActionError("error.database.save", e.toString()));
			saveErrors(request, errors);
			e.printStackTrace();
			request.setAttribute("t_information_Form", form);
			if (logger.isDebugEnabled()) {
				logger.debug("doAdd(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse) - end");
			}
			return "addpage";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("doAdd(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse) - end");
		}
		return "aftersave";
	}

}