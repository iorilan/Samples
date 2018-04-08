// mfcLab1View.h : interface of the CmfcLab1View class
//


#pragma once


class CmfcLab1View : public CView
{
protected: // create from serialization only
	CmfcLab1View();
	DECLARE_DYNCREATE(CmfcLab1View)

// Attributes
public:
	CmfcLab1Doc* GetDocument() const;

// Operations
public:

// Overrides
public:
	virtual void OnDraw(CDC* pDC);  // overridden to draw this view
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
protected:
	virtual BOOL OnPreparePrinting(CPrintInfo* pInfo);
	virtual void OnBeginPrinting(CDC* pDC, CPrintInfo* pInfo);
	virtual void OnEndPrinting(CDC* pDC, CPrintInfo* pInfo);

// Implementation
public:
	virtual ~CmfcLab1View();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in mfcLab1View.cpp
inline CmfcLab1Doc* CmfcLab1View::GetDocument() const
   { return reinterpret_cast<CmfcLab1Doc*>(m_pDocument); }
#endif

