// mfcLab2Doc.cpp : implementation of the CmfcLab2Doc class
//

#include "stdafx.h"
#include "mfcLab2.h"

#include "mfcLab2Doc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CmfcLab2Doc

IMPLEMENT_DYNCREATE(CmfcLab2Doc, CDocument)

BEGIN_MESSAGE_MAP(CmfcLab2Doc, CDocument)
END_MESSAGE_MAP()


// CmfcLab2Doc construction/destruction

CmfcLab2Doc::CmfcLab2Doc()
{
	// TODO: add one-time construction code here

}

CmfcLab2Doc::~CmfcLab2Doc()
{
}

BOOL CmfcLab2Doc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}




// CmfcLab2Doc serialization

void CmfcLab2Doc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}
}


// CmfcLab2Doc diagnostics

#ifdef _DEBUG
void CmfcLab2Doc::AssertValid() const
{
	CDocument::AssertValid();
}

void CmfcLab2Doc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG


// CmfcLab2Doc commands
