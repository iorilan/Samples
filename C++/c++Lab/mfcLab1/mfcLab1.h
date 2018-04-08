// mfcLab1.h : main header file for the mfcLab1 application
//
#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"       // main symbols


// CmfcLab1App:
// See mfcLab1.cpp for the implementation of this class
//

class CmfcLab1App : public CWinApp
{
public:
	CmfcLab1App();


// Overrides
public:
	virtual BOOL InitInstance();

// Implementation
	afx_msg void OnAppAbout();
	DECLARE_MESSAGE_MAP()
};

extern CmfcLab1App theApp;