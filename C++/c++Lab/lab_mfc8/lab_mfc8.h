// lab_mfc8.h : main header file for the PROJECT_NAME application
//

#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"		// main symbols


// Clab_mfc8App:
// See lab_mfc8.cpp for the implementation of this class
//

class Clab_mfc8App : public CWinApp
{
public:
	Clab_mfc8App();

// Overrides
	public:
	virtual BOOL InitInstance();

// Implementation

	DECLARE_MESSAGE_MAP()
};

extern Clab_mfc8App theApp;