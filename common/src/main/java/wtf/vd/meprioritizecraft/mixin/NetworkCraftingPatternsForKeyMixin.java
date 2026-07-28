package wtf.vd.meprioritizecraft.mixin;

import appeng.api.crafting.IPatternDetails;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Mixin(targets = "appeng.me.service.helpers.NetworkCraftingProviders$PatternsForKey", remap = false)
public abstract class NetworkCraftingPatternsForKeyMixin {

    @Shadow
    private Set<Object> patterns;

    @Shadow
    private List<IPatternDetails> sortedPatterns;

    @Inject(method = "sortPatterns", at = @At("HEAD"), cancellable = true)
    private void meprioritizecraft$keepPatternCandidatesAcrossPriorities(CallbackInfo ci) {
        this.sortedPatterns = this.patterns.stream()
                .sorted(Comparator.comparingInt(NetworkCraftingPatternsForKeyMixin::meprioritizecraft$getPriority)
                        .reversed())
                .map(NetworkCraftingPatternsForKeyMixin::meprioritizecraft$getPattern)
                .toList();
        ci.cancel();
    }

    @Unique
    private static int meprioritizecraft$getPriority(Object patternInfo) {
        var state = meprioritizecraft$getFieldValue(patternInfo, "state");
        if (state == null) {
            return 0;
        }
        var value = meprioritizecraft$getFieldValue(state, "priority");
        return value instanceof Integer priority ? priority : 0;
    }

    @Unique
    private static IPatternDetails meprioritizecraft$getPattern(Object patternInfo) {
        var pattern = meprioritizecraft$getFieldValue(patternInfo, "pattern");
        if (pattern instanceof IPatternDetails details) {
            return details;
        }
        throw new IllegalStateException("Could not resolve pattern details from " + patternInfo.getClass());
    }

    @Unique
    private static Object meprioritizecraft$getFieldValue(Object target, String preferredName) {
        var currentType = target.getClass();
        while (currentType != null) {
            var fields = currentType.getDeclaredFields();
            for (var field : fields) {
                if (!field.getName().equals(preferredName)) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    return field.get(target);
                } catch (ReflectiveOperationException ignored) {
                }
            }

            for (var field : fields) {
                try {
                    field.setAccessible(true);
                    var value = field.get(target);
                    if ("pattern".equals(preferredName) && value instanceof IPatternDetails) {
                        return value;
                    }
                    if ("state".equals(preferredName) && value != null && !(value instanceof IPatternDetails)
                            && meprioritizecraft$hasIntField(value)) {
                        return value;
                    }
                    if ("priority".equals(preferredName) && value instanceof Integer) {
                        return value;
                    }
                } catch (ReflectiveOperationException ignored) {
                }
            }

            currentType = currentType.getSuperclass();
        }
        return null;
    }

    @Unique
    private static boolean meprioritizecraft$hasIntField(Object target) {
        var currentType = target.getClass();
        while (currentType != null) {
            for (var field : currentType.getDeclaredFields()) {
                if (field.getType() == int.class || field.getType() == Integer.class) {
                    return true;
                }
            }
            currentType = currentType.getSuperclass();
        }
        return false;
    }
}
