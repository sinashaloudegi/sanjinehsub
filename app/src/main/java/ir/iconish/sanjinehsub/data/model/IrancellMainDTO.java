package ir.iconish.sanjinehsub.data.model;

import java.io.Serializable;


public class IrancellMainDTO implements Serializable {

    private IrancellPurchaseDTO irancellPurchaseDTO;
    private IrancellSubDTO irancellSubDTO;
    private IrancellReportDTO mIrancellReportDTO;

    public IrancellMainDTO(IrancellPurchaseDTO irancellPurchaseDTO, IrancellSubDTO irancellSubDTO, IrancellReportDTO irancellReportDTO) {
        this.irancellPurchaseDTO = irancellPurchaseDTO;
        this.irancellSubDTO = irancellSubDTO;
        mIrancellReportDTO = irancellReportDTO;
    }

    public IrancellMainDTO() {
    }

    public IrancellPurchaseDTO getIrancellPurchaseDTO() {
        return irancellPurchaseDTO;
    }

    public void setIrancellPurchaseDTO(IrancellPurchaseDTO irancellPurchaseDTO) {
        this.irancellPurchaseDTO = irancellPurchaseDTO;
    }

    public IrancellSubDTO getIrancellSubDTO() {
        return irancellSubDTO;
    }

    public void setIrancellSubDTO(IrancellSubDTO irancellSubDTO) {
        this.irancellSubDTO = irancellSubDTO;
    }

    public IrancellReportDTO getIrancellReportDTO() {
        return mIrancellReportDTO;
    }

    public void setIrancellReportDTO(IrancellReportDTO irancellReportDTO) {
        mIrancellReportDTO = irancellReportDTO;
    }

    @Override
    public String toString() {
        return "IrancellMainDTO{" +
                "irancellPurchaseDTO=" + irancellPurchaseDTO +
                ", irancellSubDTO=" + irancellSubDTO +
                ", mIrancellReportDTO=" + mIrancellReportDTO +
                '}';
    }
}
