// mfcLab2View.h : interface of the CmfcLab2View class
//


#pragma once


class CmfcLab2View : public CView
{
protected: // create from serialization only
	CmfcLab2View();
	DECLARE_DYNCREATE(CmfcLab2View)

// Attributes
public:
	CmfcLab2Doc* GetDocument() const;

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
	virtual ~CmfcLab2View();
#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

protected:

// Generated message map functions
protected:
	DECLARE_MESSAGE_MAP()
};

#ifndef _DEBUG  // debug version in mfcLab2View.cpp
inline CmfcLab2Doc* CmfcLab2View::GetDocument() const
   { return reinterpret_cast<CmfcLab2Doc*>(m_pDocument); }
#endif

